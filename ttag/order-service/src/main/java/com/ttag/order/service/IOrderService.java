package com.ttag.order.service;

import com.ttag.api.domain.dto.Result;
import com.ttag.api.domain.po.OrderDetail;
import com.ttag.api.domain.po.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 订单业务层
 */
public interface IOrderService extends IService<Order> {

    Result saveOrder(Integer num,Long productId);

    Result removeOrder(Long orderId);


    Result listMyOrderPending(Integer current);

    Result listMyOrderOther(Integer current);

    Result payOrder(String pw,Long orderId);

    OrderDetail getOrderDetailByOrderId(Long id);

    Result getOrder(Long orderId);
}
