package com.ttag.api.interceptors;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ttag.api.domain.dto.LoginUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

/**
 * 设置登录信息拦截器
 */
public class UserInfoInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String base64UserInfo = request.getHeader("user-info");
        String jsonUserInfo = null;
        if (base64UserInfo != null) {
            jsonUserInfo = new String(Base64.getDecoder().decode(base64UserInfo));
            // 使用jsonUserInfo...
        }
        if (StrUtil.isNotBlank(jsonUserInfo)){
            LoginUser loginUser = JSONUtil.toBean(jsonUserInfo, LoginUser.class);
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null, null);
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        SecurityContextHolder.clearContext();
    }
}
