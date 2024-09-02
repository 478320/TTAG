package com.ttag.api.client;

import com.ttag.api.domain.dto.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 产品客户端
 */
@FeignClient("product-service")
public interface ProductClient {
    @GetMapping("/products/detail/{id}")
    Result getProduct(@PathVariable("id")Long productId);

    @PutMapping("/products/stock/client/{id}")
    void updateProductStockClient(@PathVariable("id")Long productId, @RequestParam("num")Integer num);

}
