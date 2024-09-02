package com.ttag.order.service;

import com.ttag.api.domain.dto.Result;
import com.ttag.order.domain.po.Logistics;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 物流业务层
 */
public interface ILogisticsService extends IService<Logistics> {

    Result getOrderLogistics(Long orderId);
}
