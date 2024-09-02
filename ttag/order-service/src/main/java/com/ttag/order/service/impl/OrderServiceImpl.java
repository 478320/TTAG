package com.ttag.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ttag.api.client.ProductClient;
import com.ttag.api.client.UserClient;
import com.ttag.api.domain.MultiDelayMessage;
import com.ttag.api.domain.dto.GetUserDTO;
import com.ttag.order.domain.dto.QueueCancelOrderDTO;
import com.ttag.api.domain.po.Products;
import com.ttag.api.domain.po.User;
import com.ttag.api.domain.dto.LoginUser;
import com.ttag.api.domain.dto.Result;
import com.ttag.api.exception.BusinessException;
import com.ttag.api.utils.MQConstants;
import com.ttag.order.domain.dto.PayOrderDTO;
import com.ttag.order.domain.po.Logistics;
import com.ttag.api.domain.po.Order;
import com.ttag.api.domain.po.OrderDetail;
import com.ttag.order.domain.vo.GetOrderVO;
import com.ttag.order.domain.vo.ListOrderVO;
import com.ttag.order.mapper.LogisticsMapper;
import com.ttag.order.mapper.OrderDetailMapper;
import com.ttag.order.mapper.OrderMapper;
import com.ttag.order.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

import static com.ttag.api.utils.Code.FAIL;

/**
 * 订单的业务层实现类
 */
@Service
@RequiredArgsConstructor
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    private final ProductClient productClient;

    private final UserClient userClient;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private LogisticsMapper logisticsMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GlobalTransactional
    @Override
    public Result saveOrder(Integer num,Long productId) {
        Order order = new Order();
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        Long userId = user.getId();
        //获取用户的部分信息
        GetUserDTO userById = userClient.getUserById(userId);
        //判断用户是否填写收获地址
        if (userById.getAddress()==null){
            return Result.fail("请填写您的收货地址");
        }
        //获取货物的具体信息来构建order和orderDetail对象存入数据库
        Result result = productClient.getProduct(productId);
        Products products = BeanUtil.toBean(result.getData(), Products.class);
        order.setUserId(userId).setTotalPrice(num*products.getPrice()).setStatus("pending").setCreateAt(LocalDateTime.now());
        orderMapper.insert(order);
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderId(order.getId()).setName(products.getName()).setNum(num).setItemId(productId)
                .setPrice(products.getPrice()).setImage(products.getImage());
        orderDetailMapper.insert(orderDetail);
        //扣减库存
        String purchasableStatus = "accept";
        if (!products.getStatus().equals(purchasableStatus)){
            return Result.fail("该产品尚未通过审核，暂时无法购买");
        }
        try {
            productClient.updateProductStockClient(productId,products.getStock()-num);
        } catch (Exception e) {
            throw new BusinessException(FAIL,"该产品订单库存不足");
        }
        //mq发送延迟消息强制取消订单
        QueueCancelOrderDTO queueCancelOrderDTO = new QueueCancelOrderDTO();
        queueCancelOrderDTO.setNum(num);
        queueCancelOrderDTO.setProductId(productId);
        queueCancelOrderDTO.setOrderId(order.getId());
        MultiDelayMessage<QueueCancelOrderDTO> msg = MultiDelayMessage.of(queueCancelOrderDTO, 10000L, 20000L, 30000L, 45000L, 60000L, 90000L, 180000L,360000L,720000L);
        rabbitTemplate.convertAndSend(MQConstants.ORDER_EXCHANGE_DELAY, MQConstants.ORDER_CANCEL_KEY,
                msg, new MessagePostProcessor() {
                    @Override
                    public Message postProcessMessage(Message message) throws AmqpException {
                        message.getMessageProperties().setDelay(msg.removeNextDelay().intValue());
                        return message;
                    }
                });
        return Result.ok("新增订单成功");
    }

    @Transactional
    @Override
    public Result removeOrder(Long orderId) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        Long userId = user.getId();
        Order order = orderMapper.selectById(orderId);
        //判断要删除订单的人是否是用户本人
        if (!order.getUserId().equals(userId)){
            return Result.fail("你没有权限进行次操作");
        }
        //判断订单的状态是否支持删除
        if (!"completed".equals(order.getStatus())&&!"cancel".equals(order.getStatus())){
            return Result.fail("尚未完成的订单不支持删除");
        }
        orderMapper.deleteById(orderId);
        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId,orderId);
        orderDetailMapper.delete(queryWrapper);
        return Result.ok("删除订单成功");
    }


    @Override
    public Result listMyOrderPending(Integer current) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        Long userId = user.getId();
        List<ListOrderVO> list = orderMapper.listMyOrderPending(userId,current*4);
        return Result.ok(list,list.size());
    }

    @Override
    public Result listMyOrderOther(Integer current) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        Long userId = user.getId();
        List<ListOrderVO> list = orderMapper.listMyOrderOther(userId,current*4);
        return Result.ok(list,list.size());
    }

    @Override
    @GlobalTransactional
    public Result payOrder(String pw,Long orderId) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        Long userId = user.getId();
        PayOrderDTO payOrder = orderMapper.getPayOrder(orderId);
        String pendingStatus = "pending";
        //判断用户是否是订单的拥有者
        if (!payOrder.getUserId().equals(userId)){
            return Result.fail("你没有权限进行此操作");
        //判断用户的订单是否已经支付
        }else if (!payOrder.getStatus().equals(pendingStatus)){
            return Result.fail("您已经付过款");
        }
        Long itemId = payOrder.getItemId();
        Result productResult = productClient.getProduct(itemId);
        Products products = BeanUtil.toBean(productResult.getData(), Products.class);
        String purchasableStatus = "accept";
        //判断该产品是否上架
        if (!products.getStatus().equals(purchasableStatus)){
            return Result.fail("该产品尚未通过审核，暂时无法购买");
        }
        //转账
        Long farmerId = products.getFarmerId();
        try {
            userClient.transferMoney(pw,payOrder.getTotalPrice(),farmerId);
        } catch (Exception e) {
            throw new BusinessException(FAIL,"用户余额不足");
        }
        //生成物流订单
        Logistics logistics = new Logistics();
        logistics.setStatus("pending").setOrderId(orderId).setCourier("等待送货人员中").setTrackingNumber("正在生成");
        rabbitTemplate.convertAndSend(MQConstants.ORDER_EXCHANGE, MQConstants.SAVE_LOGISTICS_KEY, JSONUtil.toJsonStr(logistics));
        //改变订单状态
        orderMapper.updateStatusComplete(orderId);
        return Result.ok("支付成功,耐心等待货物到达亲");
    }

    @Override
    public OrderDetail getOrderDetailByOrderId(Long id) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        Long userId = user.getId();
        PayOrderDTO payOrder = orderMapper.getPayOrder(id);
        if (!payOrder.getUserId().equals(userId)){
            return null;
        }
        LambdaQueryWrapper<OrderDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderDetail::getOrderId,id);
        OrderDetail orderDetail = orderDetailMapper.selectOne(lambdaQueryWrapper);
        return orderDetail;
    }

    @Override
    public Result getOrder(Long orderId) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        Long userId = user.getId();
        GetOrderVO orderById = orderMapper.getOrderById(orderId,userId);
        return Result.ok(orderById);
    }
}
