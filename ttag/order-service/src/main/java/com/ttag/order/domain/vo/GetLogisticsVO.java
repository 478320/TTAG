package com.ttag.order.domain.vo;

import com.ttag.api.domain.po.Products;
import com.ttag.order.domain.po.Logistics;
import lombok.Data;

/**
 * 获取物流信息前端返回对象
 */
@Data
public class GetLogisticsVO {

    private Logistics logistics;

    private Products products;

    private Integer totalPrice;
}
