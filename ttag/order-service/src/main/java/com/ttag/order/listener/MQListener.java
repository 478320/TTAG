package com.ttag.order.listener;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.ttag.api.client.ProductClient;
import com.ttag.api.domain.MultiDelayMessage;
import com.ttag.api.domain.dto.Result;
import com.ttag.api.domain.po.Products;
import com.ttag.order.domain.dto.QueueCancelOrderDTO;
import com.ttag.api.utils.MQConstants;
import com.ttag.order.domain.po.Logistics;
import com.ttag.api.domain.po.Order;
import com.ttag.order.mapper.LogisticsMapper;
import com.ttag.order.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * MQ的消息监听器
 */
@Component
@RequiredArgsConstructor
public class MQListener {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private IOrderService orderService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private LogisticsMapper logisticsMapper;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MQConstants.ORDER_CANCEL_QUEUE, durable = "true",
            arguments = @Argument(name = "x-queue-mode",
                    value = "lazy")),
            exchange = @Exchange(name = MQConstants.ORDER_EXCHANGE_DELAY, type = ExchangeTypes.DIRECT,delayed = "true"),
            key = MQConstants.ORDER_CANCEL_KEY
    ))
    public void listenDelayOrderMessage(MultiDelayMessage<QueueCancelOrderDTO> msg,@Header(AmqpHeaders.MESSAGE_ID) String messageId)throws InterruptedException{
        //从redis中获取消息的Id，这一步来保证mq的业务幂等性
        String s = stringRedisTemplate.opsForValue().get(messageId);
        if ("1".equals(s)) {
            //如果redis中已经存在该消息则直接返回不做处理
            return;
        }
        //构造取消订单的数据传输对象
        QueueCancelOrderDTO queueCancelOrderDTO = msg.getData();
        Long orderId = queueCancelOrderDTO.getOrderId();
        Order order = orderService.getById(orderId);
        if (order == null || "paid".equals(order.getStatus())){
            //订单不存在或已经被处理
            return;
        }
        //判断是否存在延迟取消订单时间
        if (msg.hasNextDelay()){
            Long nextDelay = msg.removeNextDelay();
            //如果有就将延迟时间数组指针向后移动，并用这次的延迟时间重新发送延迟消息
            rabbitTemplate.convertAndSend(MQConstants.ORDER_EXCHANGE_DELAY, MQConstants.ORDER_CANCEL_KEY,
                    msg, new MessagePostProcessor() {
                        @Override
                        public Message postProcessMessage(Message message) throws AmqpException {
                            message.getMessageProperties().setDelay(nextDelay.intValue());
                            return message;
                        }
                    });
            return;
        }
        //超过延迟时间用户还未支付则取消订单
        orderService.lambdaUpdate()
                .set(Order::getStatus,"cancel")
                .eq(Order::getId,orderId)
                .update();
        // 恢复库存
        Long productId = queueCancelOrderDTO.getProductId();
        Result productResult = productClient.getProduct(productId);
        Products data = BeanUtil.toBean(productResult.getData(), Products.class);
        productClient.updateProductStockClient(productId,data.getStock()+queueCancelOrderDTO.getNum());
        //向redis中保存该消息的Id信息
        stringRedisTemplate.opsForValue().set(messageId, "1", 10, TimeUnit.SECONDS);

    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MQConstants.SAVE_LOGISTICS_QUEUE, durable = "true",
                    arguments = @Argument(name = "x-queue-mode",
                            value = "lazy")),
            exchange = @Exchange(name = MQConstants.ORDER_EXCHANGE, type = ExchangeTypes.DIRECT),
            key = MQConstants.SAVE_LOGISTICS_KEY
    ))
    public void listenSaveLogisticsMessage(String logisticsStr,@Header(AmqpHeaders.MESSAGE_ID) String messageId)throws InterruptedException {
        //从redis中获取消息的Id
        String s = stringRedisTemplate.opsForValue().get(messageId);
        if ("1".equals(s)) {
            //如果redis中已经存在该消息则直接返回不做处理
            return;
        }
        logisticsMapper.insert(JSONUtil.toBean(logisticsStr, Logistics.class));
        //向redis中保存该消息的Id信息
        stringRedisTemplate.opsForValue().set(messageId, "1", 10, TimeUnit.SECONDS);
    }
}
