package com.ttag.api.domain.dto;

import lombok.Data;

/**
 * 更新产品信息数据传输对象
 */
@Data
public class UpdateProductDTO {

    private Long id;

    private String name;

    private Integer price;

    private Integer stock;

    private String description;

    private String image;

    private String shipAddress;

    private String service;
}
