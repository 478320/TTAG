package com.ttag.api.domain.vo;

import lombok.Data;

/**
 * 获取我的前端返回对象
 */
@Data
public class MyUserVO {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String username;

    private String fullName;

    private String address;

    private String role;

    private String picture;

    private String email;

}
