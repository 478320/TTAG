package com.ttag.api.config;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.ttag.api.domain.dto.LoginUser;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * 登录拦截器
 */
@Configuration
public class DefaultFeignConfig {


    @Bean
    public RequestInterceptor userInfoRequestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // 获取登录用户
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication == null) {
                    // 如果Authentication为null，则直接跳过
                    return;
                }

                // 获取principal
                Object principal = authentication.getPrincipal();
                if (principal == null || principal.equals("anonymousUser")) {
                    // 如果principal为空或等于"anonymousUser"，则直接跳过
                    return;
                }
                LoginUser user = (LoginUser)principal;
                String base64UserInfo = Base64.getEncoder().encodeToString(JSONUtil.toJsonStr(user).getBytes());
                // 如果不为空则放入请求头中，传递给下游微服务
                template.header("user-info", base64UserInfo);
            }
        };
    }


}
