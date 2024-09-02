package com.ttag.api.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 产品实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Products implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private Integer price;

    private Integer stock;

    private String description;

    private Long farmerId;

    private String image;

    private String shipAddress;

    private String service;

    private Double score;

    private String status;


}
