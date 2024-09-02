package com.ttag.user.domain.dto;


import lombok.Data;

/**
 * 登录的数据传输对象
 */
@Data
public class LoginUserDTO {

    private String username;

    private String password;

}
