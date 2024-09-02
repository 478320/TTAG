package com.ttag.api.client;

import com.ttag.api.domain.dto.Result;
import com.ttag.api.domain.dto.GetUserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 用户客户端
 */
@FeignClient("user-service")
public interface UserClient {

    @GetMapping("user/me")
    Result getMe();

    @GetMapping("user/{id}")
    GetUserDTO getUserById(@PathVariable("id")Long id);

    @PutMapping("/user/money/transfer/{id}")
    void transferMoney(@RequestParam("pw") String pw, @RequestParam("amount") Integer amount, @PathVariable("id")Long receiveUserId);

}
