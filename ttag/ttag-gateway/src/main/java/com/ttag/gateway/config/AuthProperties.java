package com.ttag.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

/**
 * 路径加载类
 */
@Data
@Component
@ConfigurationProperties(prefix = "ttag.auth")
public class AuthProperties {
    private List<String> includePaths;
    private List<String> excludePaths;


}


