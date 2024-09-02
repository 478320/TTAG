package com.ttag.user.service;

import com.ttag.api.domain.po.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ttag.api.domain.dto.Result;
import com.ttag.user.domain.dto.*;

/**
 * 用户业务层
 */
public interface IUserService extends IService<User> {

    Result login(LoginUserDTO loginUserDTO);

    Result register(RegisterUserDTO registerUserDTO);

    Result permisson(Long userId);

    Result getMe();

    Result getRequest(Integer current);

    Result setHeadPortrait(HeadPortraitDTO headPortraitDTO);

    Result beFarmer(BeFarmerDTO beFarmerDTO);

    Result setAddress(AddressDTO addressDTO);

    Result logOut();

    void transferMoney(String pw, Integer amount,Long receiveUserId);

    Result reject(Long userId);

    Result setEmail(EmailDTO emailDTO);
}
