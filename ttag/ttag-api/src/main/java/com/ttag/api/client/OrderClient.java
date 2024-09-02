package com.ttag.api.client;

import com.ttag.api.domain.dto.Result;
import com.ttag.api.domain.po.Order;
import com.ttag.api.domain.po.OrderDetail;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 订单客户端
 */
@FeignClient("order-service")
public interface OrderClient {

    @GetMapping("order/product/detail/{id}")
    OrderDetail getProduct(@PathVariable("id")Long orderId);

    @GetMapping("order/{id}")
    Result getOrder(@PathVariable("id")Long orderId);

    @GetMapping("order/client/{id}")
    Order selectOrder(@PathVariable("id")Long orderId);


}
