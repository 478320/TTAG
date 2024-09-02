package com.ttag.api.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 订单详情实体类
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_order_detail")
public class OrderDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单详情id 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * sku商品id
     */
    private Long itemId;

    /**
     * 购买数量
     */
    private Integer num;

    /**
     * 商品标题
     */
    private String name;

    /**
     * 价格,单位：分
     */
    private Integer price;

    /**
     * 商品图片
     */
    private String image;



}
