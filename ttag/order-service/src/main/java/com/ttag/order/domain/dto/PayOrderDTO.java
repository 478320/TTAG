package com.ttag.order.domain.dto;

import lombok.Data;

/**
 * 支付订单数据传递对象
 */
@Data
public class PayOrderDTO {

    private Long userId;

    private Integer totalPrice;

    private String status;

    /**
     * sku商品id
     */
    private Long itemId;

    /**
     * 购买数量
     */
    private Integer num;
}
