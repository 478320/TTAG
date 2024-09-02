package com.ttag.order.controller;

import com.ttag.api.domain.dto.Result;
import com.ttag.order.service.ILogisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 物流信息表现层
 */
@RestController
@RequestMapping("/logistics")
public class LogisticsController {
    @Autowired
    private ILogisticsService logisticsService;

    /**
     * 获取订单的物流信息
     *
     * @param orderId 订单的Id
     * @return 订单的物流信息
     */
    @GetMapping("/{id}")
    public Result getOrderLogistics(@PathVariable("id")Long orderId){
        return logisticsService.getOrderLogistics(orderId);
    }
}
