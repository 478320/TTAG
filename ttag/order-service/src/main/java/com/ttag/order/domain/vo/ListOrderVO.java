package com.ttag.order.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 获取多个订单前端返回对象
 */
@Data
public class ListOrderVO {

    private Long id;

    private LocalDateTime createAt;

    private String status;

    private Integer totalPrice;

    private Long itemId;

    private Integer price;
    /**
     * 商品标题
     */
    private String name;

    /**
     * 商品图片
     */
    private String image;

    private Integer num;


}
