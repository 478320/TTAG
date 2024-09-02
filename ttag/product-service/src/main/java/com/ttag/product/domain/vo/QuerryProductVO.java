package com.ttag.product.domain.vo;

import lombok.Data;

/**
 * 查询产品具体信息的前端返回对象
 */
@Data
public class QuerryProductVO {

    private Long id;

    private String name;

    private Integer price;

    private Integer stock;

    private String image;

    private String description;

    private Double score;

    private String status;

    private String shipAddress;
}
