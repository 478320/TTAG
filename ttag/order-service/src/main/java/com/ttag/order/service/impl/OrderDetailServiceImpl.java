package com.ttag.order.service.impl;

import com.ttag.api.domain.po.OrderDetail;
import com.ttag.order.mapper.OrderDetailMapper;
import com.ttag.order.service.IOrderDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 订单详情的业务层实现类
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements IOrderDetailService {

}
