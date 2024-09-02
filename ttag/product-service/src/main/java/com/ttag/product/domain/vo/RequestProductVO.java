package com.ttag.product.domain.vo;

import com.ttag.api.domain.po.Products;
import lombok.Data;

/**
 * 获取农户申请产品信息的数据传输对象
 */
@Data
public class RequestProductVO {

    private Products products;

    private String email;

    private String username;

    private String picture;
}
