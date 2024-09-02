package com.ttag.order.domain.dto;

import lombok.Data;

/**
 * mq取消订单数据传输对象
 */
@Data
public class QueueCancelOrderDTO {

    private Long productId;

    private Integer num;

    private Long orderId;
}
