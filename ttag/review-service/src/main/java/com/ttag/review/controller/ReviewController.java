package com.ttag.review.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ttag.api.domain.dto.Result;
import com.ttag.review.domain.dto.AddProductReviewDTO;
import com.ttag.review.domain.po.Review;
import com.ttag.review.service.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 评论的表现层对象
 */
@RestController
@RequestMapping("/review")
public class ReviewController {

    @Autowired
    private IReviewService reviewService;

    @GetMapping("/product/paging/{productId}")
    public Result listProductReviewPage(@PathVariable("productId")Long productId, @RequestParam("current")Integer current){
        return reviewService.listProductReviewPage(productId,current);
    }

    @PostMapping("/add/product")
    public Result addProductReview(@RequestBody AddProductReviewDTO addProductReviewDTO){
        return reviewService.addProductReview(addProductReviewDTO);
    }

    @GetMapping("/product/{productId}")
    public Double listProductRating(@PathVariable("productId")Long productId){
        LambdaQueryWrapper<Review> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Review::getProductId,productId);
        List<Review> reviewList = reviewService.list(lambdaQueryWrapper);
        double total = 0;
        for (Review review : reviewList) {
            total = total+review.getRating();
        }
        Double ave = total/reviewList.size();
        return ave;
    }

}
