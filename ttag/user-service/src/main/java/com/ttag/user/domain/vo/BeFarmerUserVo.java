package com.ttag.user.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 农户信息的前端返回对象
 */
@Data
public class BeFarmerUserVo {

    private Long id;

    private String username;

    private String email;

    private String address;

    private String picture;
}
