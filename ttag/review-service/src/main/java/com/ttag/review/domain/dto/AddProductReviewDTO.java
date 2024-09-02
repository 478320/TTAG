package com.ttag.review.domain.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 添加评论的数据传输对象
 */
@Data
public class AddProductReviewDTO {

    private Long orderId;

    private Integer rating;

    private String comment;
}
