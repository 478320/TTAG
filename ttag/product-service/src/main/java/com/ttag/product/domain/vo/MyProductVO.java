package com.ttag.product.domain.vo;

import lombok.Data;

/**
 * 获取我的产品的前端返回对象
 */
@Data
public class MyProductVO {

    private Long id;

    private String name;

    private Integer price;

    private Integer stock;

    private String image;

    private String description;

    private Double score;

    private String status;
}
