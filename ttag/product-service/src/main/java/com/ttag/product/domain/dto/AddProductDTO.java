package com.ttag.product.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 新增产品的数据传输对象
 */
@Data
public class AddProductDTO {

    private String name;

    private Integer price;

    private Integer stock;

    private String description;

    private String image;

    private String shipAddress;

    private String service;
}
