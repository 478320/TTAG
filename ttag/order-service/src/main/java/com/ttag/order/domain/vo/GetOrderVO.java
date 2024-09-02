package com.ttag.order.domain.vo;

import lombok.Data;

/**
 * 获取订单详细信息前端返回对象
 */
@Data
public class GetOrderVO {

    private Long id;

    private Integer totalPrice;

    private Integer price;

    private Integer num;

    /**
     * 商品标题
     */
    private String name;

    /**
     * 商品图片
     */
    private String image;
}
