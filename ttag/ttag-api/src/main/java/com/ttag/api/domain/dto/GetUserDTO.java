package com.ttag.api.domain.dto;

import lombok.Data;

/**
 * 获取用户数据传输对象
 */
@Data
public class GetUserDTO {

    private String head;

    private String username;

    private String email;

    private String role;

    private String address;
}
