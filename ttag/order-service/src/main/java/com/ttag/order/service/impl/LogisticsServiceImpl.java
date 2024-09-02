package com.ttag.order.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ttag.api.client.ProductClient;
import com.ttag.api.domain.dto.LoginUser;
import com.ttag.api.domain.dto.Result;
import com.ttag.api.domain.po.OrderDetail;
import com.ttag.api.domain.po.Products;
import com.ttag.api.domain.po.User;
import com.ttag.order.domain.po.Logistics;
import com.ttag.order.domain.vo.GetLogisticsVO;
import com.ttag.order.mapper.LogisticsMapper;
import com.ttag.order.mapper.OrderDetailMapper;
import com.ttag.order.mapper.OrderMapper;
import com.ttag.order.service.ILogisticsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * 物流信息的业务层实现类
 */
@Service
public class LogisticsServiceImpl extends ServiceImpl<LogisticsMapper, Logistics> implements ILogisticsService {

    @Autowired
    private LogisticsMapper logisticsMapper;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Override
    public Result getOrderLogistics(Long orderId) {
        GetLogisticsVO getLogisticsVO = new GetLogisticsVO();
        //获取订单详情信息
        LambdaQueryWrapper<OrderDetail> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderDetail::getOrderId,orderId);
        OrderDetail orderDetail = orderDetailMapper.selectOne(lambdaQueryWrapper);
        //获取物流详情信息
        LambdaQueryWrapper<Logistics> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Logistics::getOrderId,orderId);
        Logistics logistics = logisticsMapper.selectOne(queryWrapper);
        //获取订单对应的货物的详情信息
        Result product = productClient.getProduct(orderDetail.getItemId());
        Products products = BeanUtil.toBean(product.getData(), Products.class);
        //构件获取物流前端返回数据对象
        getLogisticsVO.setProducts(products);
        getLogisticsVO.setLogistics(logistics);
        getLogisticsVO.setTotalPrice(orderDetail.getNum()*orderDetail.getPrice());
        return Result.ok(getLogisticsVO);
    }
}
