package com.ttag.review.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ttag.review.domain.po.Review;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 查询评论的前端返回对象
 */
@Data
public class ListProductReviewDTO {

    private String username;

    private String head;

    private Review review;

}
