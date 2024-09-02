package com.ttag.product.listener;

import cn.hutool.json.JSONUtil;
import com.ttag.api.client.ReviewClient;

import com.ttag.api.domain.po.Products;
import com.ttag.api.utils.MQConstants;
import com.ttag.product.mapper.ProductsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
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
    private ProductsMapper productsMapper;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private final ReviewClient reviewClient;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = MQConstants.CALCULATE_SCORE_QUEUE,
                    durable = "true",
                    arguments = @Argument(name = "x-queue-mode",
                            value = "lazy")),
            exchange = @Exchange(name = MQConstants.SCORE_EXCHANGE, type = ExchangeTypes.DIRECT),
            key = {MQConstants.CALCULATE_SCORE_KEY}
    ))
    public void listenSimpleQueueMessage(Long productId, @Header(AmqpHeaders.MESSAGE_ID) String messageId) throws InterruptedException {
        //从redis中获取消息的Id
        String s = stringRedisTemplate.opsForValue().get(messageId);
        if ("1".equals(s)) {
            //如果redis中已经存在该消息则直接返回不做处理
            return;
        }
        //重新计算产品的评分
        Double ave = reviewClient.listProductRating(productId);
        Products products = new Products().setId(productId).setScore(ave);
        productsMapper.updateById(products);
        //向redis中保存该消息的Id信息
        stringRedisTemplate.opsForValue().set(messageId, "1", 10, TimeUnit.SECONDS);
    }
}
