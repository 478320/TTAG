package com.ttag.review.service;

import com.ttag.api.domain.dto.Result;
import com.ttag.review.domain.dto.AddProductReviewDTO;
import com.ttag.review.domain.po.Review;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 评论的业务层
 */
public interface IReviewService extends IService<Review> {

    Result listProductReviewPage(Long productId, Integer current);

    Result addProductReview(AddProductReviewDTO addProductReviewDTO);
}
