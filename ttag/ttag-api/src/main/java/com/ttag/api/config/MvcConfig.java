package com.ttag.api.config;

import com.ttag.api.interceptors.UserInfoInterceptor;
import com.ttag.api.utils.JsonObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * web配置类
 * 包含登录拦截器，前端返回处理器
 */
@Configuration
@ConditionalOnClass(DispatcherServlet.class)
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserInfoInterceptor());
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 创建消息转换器对象
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        // 设置对象转换器，底层使用Jackson将对象转为json
        messageConverter.setObjectMapper(new JsonObjectMapper());
        // 将上面的消息转换器对象追加到mvc框架的转换器中，放置前面优先使用
        converters.add(0, messageConverter);
    }

}
