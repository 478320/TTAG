package com.ttag.user.domain.dto;

import lombok.Data;

/**
 * 注册用户的数据传输对象
 */
@Data
public class RegisterUserDTO {

    private String username;

    private String password;

    private String phoneNumber;

}
