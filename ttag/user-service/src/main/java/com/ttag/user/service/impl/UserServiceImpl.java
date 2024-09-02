package com.ttag.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ttag.api.domain.po.User;
import com.ttag.api.domain.dto.LoginUser;
import com.ttag.api.domain.dto.Result;
import com.ttag.api.exception.BusinessException;
import com.ttag.api.utils.JwtUtil;
import com.ttag.api.utils.RedisConstans;
import com.ttag.user.domain.dto.*;
import com.ttag.user.domain.vo.BeFarmerUserVo;
import com.ttag.api.domain.vo.MyUserVO;
import com.ttag.user.mapper.UserMapper;
import com.ttag.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.ttag.api.utils.Code.FAIL;

/**
 * 用户业务层实现类
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserMapper userMapper;

    private static final String PHONE_NUMBER_PATTERN = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$";

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";



    @Override
    public Result login(LoginUserDTO loginUserDTO) {
        //AuthenticationManager authenticate进行用户认证
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDTO.getUsername(), loginUserDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //如果认证没通过，给出对应的提示
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }
        //如果认证通过了，使用userid生成一个jwt jwt存入ResponseResult返回
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userid = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userid);
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        //把完整的用户信息存入redis  userid作为key
        String loginUserStr = JSONUtil.toJsonStr(loginUser);
        stringRedisTemplate.opsForValue().set(RedisConstans.LOGIN_USER_KEY + userid, loginUserStr, 72, TimeUnit.HOURS);
        return Result.ok("登录成功",map);
    }

    @Override
    public Result register(RegisterUserDTO registerUserDTO) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String phoneNumber = registerUserDTO.getPhoneNumber();
        //判断电话是否为空
        if (StringUtil.isNullOrEmpty(phoneNumber)){
            return Result.fail("请填写您的电话号码");
        }
        //判断电话格式是否符合正则
        if (!phoneNumber.matches(PHONE_NUMBER_PATTERN)) {
            return Result.fail("您的电话格式有误请确认");
        }
        //创建用户
        User user = BeanUtil.copyProperties(registerUserDTO, User.class);
        String encodePassword = passwordEncoder.encode(user.getPassword());
        String encodePhoneNumber = passwordEncoder.encode(user.getPhoneNumber());
        user.setPassword(encodePassword);
        user.setPhoneNumber(encodePhoneNumber);
        try {
            userMapper.insert(user);
        } catch (Exception e) {
            throw new BusinessException(FAIL,"注册失败该用户名或电话号码已经被使用过");
        }
        return Result.ok("创建用户成功");
    }

    @Override
    public Result permisson(Long userId) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userX = loginUser.getUser();
        User user = userMapper.selectById(userX);
        String limitUser = "admin";
        //判断用户权限
        if (!limitUser.equals(user.getRole())){
            return Result.fail("你没有权限进行此操作");
        }
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",userId).set("role","farmer");
        update(updateWrapper);
        return Result.ok("操作成功");
    }

    @Override
    public Result reject(Long userId) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userX = loginUser.getUser();
        User user = userMapper.selectById(userX);
        String limitUser = "admin";
        //判断用户权限
        if (!limitUser.equals(user.getRole())){
            return Result.fail("你没有权限进行此操作");
        }
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",userId).set("role","user");
        update(updateWrapper);
        return Result.ok("操作成功,请及时联系农户告知原因并指导其成为农户");
    }

    @Override
    public Result getMe() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        User select = userMapper.selectById(user);
        MyUserVO myUserVO = BeanUtil.copyProperties(select, MyUserVO.class);
        return Result.ok(myUserVO);
    }

    @Override
    public Result getRequest(Integer current) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User userX = loginUser.getUser();
        User user = userMapper.selectById(userX);
        String limitUser = "admin";
        //判断用户权限
        if (!limitUser.equals(user.getRole())){
            return Result.fail("你没有权限进行此操作");
        }
        IPage page1 = new Page(current,12);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("request_time").eq("role","request");
        IPage page = userMapper.selectPage(page1, queryWrapper);
        // 获取当前页数据
        List<User> records = page.getRecords();
        if (records.isEmpty() || records == null){
            return Result.ok(Collections.emptyList(),0);
        }
        // 查询用户
        List<BeFarmerUserVo> collect = records.stream().map(user1 -> {
            return BeanUtil.copyProperties(user1, BeFarmerUserVo.class);
        }).collect(Collectors.toList());
        return Result.ok(collect,collect.size());
    }

    @Override
    public Result beFarmer(BeFarmerDTO beFarmerDTO) {
        String email = beFarmerDTO.getEmail();
        //判断用户是否填写邮箱
        if(email.isEmpty()){
            return Result.fail("请完善您的邮箱信息，方便我们进一步联系您");
        }
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        String accessRole = "user";
        //判断用户身份是否正确
        if (!user.getRole().equals(accessRole)){
            return Result.fail("您已经成为农户，请勿重复提交申请");
        }
        Long userId = user.getId();
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",userId).set("email", email).set("role","request").set("request_time", LocalDateTime.now());
        update(updateWrapper);
        return Result.ok("发送请求成功，请注意审核邮件");
    }

    @Override
    public Result setHeadPortrait(HeadPortraitDTO headPortraitDTO) {
        String picture = headPortraitDTO.getPicture();
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        Long userId = user.getId();
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",userId).set("picture",picture);
        update(updateWrapper);
        return Result.ok("更新头像成功",picture);
    }

    @Override
    public Result setAddress(AddressDTO addressDTO) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        Long userId = user.getId();
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",userId).set("address",addressDTO.getAddress());
        update(updateWrapper);
        return Result.ok("更新地址成功");
    }

    @Override
    public Result setEmail(EmailDTO emailDTO) {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        Long userId = user.getId();
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        String email = emailDTO.getEmail();
        //判断邮箱是否符合正则
        if(!email.matches(EMAIL_PATTERN)){
            return Result.fail("您的邮箱格式有误，请检查您的邮箱是否填写正确");
        }
        updateWrapper.eq("id",userId).set("email", email);
        update(updateWrapper);
        return Result.ok("更新邮箱成功");
    }

    @Override
    public Result logOut() {
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        Long userId = user.getId();
        //删除登录信息
        stringRedisTemplate.delete(RedisConstans.LOGIN_USER_KEY + userId);
        return Result.ok("退出登录成功");
    }

    @Override
    public void transferMoney(String pw, Integer totalFee,Long receiveUserId) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 1.校验密码
        LoginUser loginUser = (LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = loginUser.getUser();
        User select = userMapper.selectById(user);
        if (select == null || !passwordEncoder.matches(pw, select.getPassword())) {
            // 密码错误
            throw new BusinessException(FAIL, "用户密码错误");
        }
        // 2.尝试扣款
        try {
            userMapper.deductMoney(select.getId(), totalFee);
        } catch (Exception e) {
            throw new BusinessException(FAIL,"扣款失败，可能是余额不足！");
        }
            userMapper.addMoney(receiveUserId,totalFee);
    }
}
