package com.ttag.api.client;

import com.ttag.api.domain.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 评论客户端
 */
@FeignClient("review-service")
public interface ReviewClient {

    @GetMapping("review/product/{productId}")
    Double listProductRating(@PathVariable("productId")Long productId);

}
