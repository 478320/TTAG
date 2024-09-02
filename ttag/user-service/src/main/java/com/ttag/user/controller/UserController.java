package com.ttag.user.controller;


import com.ttag.api.domain.po.User;
import com.ttag.api.domain.dto.Result;
import com.ttag.user.domain.dto.*;
import com.ttag.api.domain.dto.GetUserDTO;
import com.ttag.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户的表现层对象
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    /**
     * 登录
     *
     * @param loginUserDTO 登录的用户
     * @return 用户登录凭证
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginUserDTO loginUserDTO) {
        //登录
        return userService.login(loginUserDTO);
    }

    /**
     * 注册
     *
     * @param registerUserDTO 注册的用户
     */
    @PostMapping("/register")
    public Result register(@RequestBody RegisterUserDTO registerUserDTO) {
        return userService.register(registerUserDTO);
    }

    /**
     * 获取我的信息
     *
     * @return 我的信息
     */
    @GetMapping("/me")
    public Result getMe(){
        return userService.getMe();
    }

    /**
     * 设置我的头像
     *
     * @param headPortraitDTO 设置头像数据传输类
     * @return 是否设置成功
     */
    @PutMapping("/head")
    public Result setHeadPortrait(@RequestBody HeadPortraitDTO headPortraitDTO){
        return userService.setHeadPortrait(headPortraitDTO);
    }

    /**
     * 设置我的地址信息
     *
     * @param addressDTO 设置地址数据传输类
     * @return 是否设置成功
     */
    @PutMapping("/address")
    public Result setAddress(@RequestBody AddressDTO addressDTO){
        return userService.setAddress(addressDTO);
    }

    /**
     * 设置我的邮箱信息
     *
     * @param emailDTO 设置邮箱的数据传输类
     * @return 是否设置成功
     */
    @PutMapping("/email")
    public Result setEmail(@RequestBody EmailDTO emailDTO){
        return userService.setEmail(emailDTO);
    }

    /**
     * 成为农户
     *
     * @param beFarmerDTO 成为农户的信息
     * @return 是否成功发送申请
     */
    @PutMapping("/be/farmer")
    public Result beFarmer(@RequestBody BeFarmerDTO beFarmerDTO){
        return userService.beFarmer(beFarmerDTO);
    }

    /**
     * 管理员，允许用户成为农户
     *
     * @param userId 用户的ID
     * @return 是否操作成功
     */
    @PutMapping("/admin/permission/{userId}")
    public Result permission(@PathVariable("userId") Long userId){
        return userService.permisson(userId);
    }

    /**
     * 管理员，拒绝用户成为农户
     *
     * @param userId 用户的ID
     * @return 是否操作成功
     */
    @PutMapping("/admin/reject/{userId}")
    public Result reject(@PathVariable("userId") Long userId){
        return userService.reject(userId);
    }

    /**
     * 获取农户申请的表单
     *
     * @param current 获取的页数
     * @return 农户申请的信息
     */
    @GetMapping("/admin/request")
    public Result getRequest(@RequestParam(value = "current", defaultValue = "0") Integer current){
        return userService.getRequest(current);
    }

    /**
     * 退出登录
     *
     * @return 是否退出成功
     */
    @DeleteMapping("/logout")
    public Result logOut(){
        return userService.logOut();
    }

    /**
     * 内部方法，转账
     *
     * @param pw 用户密码
     * @param amount 转账总数
     * @param receiveUserId 转账给谁
     */
    @PutMapping("/money/transfer/{id}")
    public void transferMoney(@RequestParam("pw") String pw,@RequestParam("amount") Integer amount,@PathVariable("id")Long receiveUserId){
        userService.transferMoney(pw, amount,receiveUserId);
    }

    /**
     * 内部方法，获取用户信息
     *
     * @param id 用户id
     * @return 用户的部分信息
     */
    @GetMapping("/{Id}")
    public GetUserDTO getUserById(@PathVariable("Id")Long id){
        User user = userService.getById(id);
        GetUserDTO getUserDTO = new GetUserDTO();
        getUserDTO.setUsername(user.getUsername());
        getUserDTO.setEmail(user.getEmail());
        getUserDTO.setHead(user.getPicture());
        getUserDTO.setRole(user.getRole());
        getUserDTO.setAddress(user.getAddress());
        return getUserDTO;
    }
}
