package com.ttag.gateway.config;

import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * 跨域处理器
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsFilter1() {

        CorsConfiguration config = new CorsConfiguration();
        // 允许的来源
        config.addAllowedOrigin("*");
        // 允许的请求方法
        config.addAllowedMethod("*");
        // 允许的请求头
        config.addAllowedHeader("*");
        // 是否允许携带cookie
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public WebFilter corsFilter() {
        return new WebFilter() {
            @Override
            public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
                ServerHttpRequest request = exchange.getRequest();
                ServerHttpResponse response = exchange.getResponse();

                // Set the headers you need
                response.getHeaders().add("Access-Control-Allow-Origin", "*");
                response.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS");
                response.getHeaders().add("Access-Control-Allow-Headers", "*");
                response.getHeaders().add("Access-Control-Expose-Headers", "*");

                // If it's a preflight request, we set the headers and terminate the request
                if (request.getMethod() == HttpMethod.OPTIONS) {
                    response.getHeaders().add("Access-Control-Max-Age", "3600");
                    response.setStatusCode(HttpStatus.OK);
                    return Mono.empty();
                }

                // For non-OPTIONS requests, we just pass the request through the filter chain
                return chain.filter(exchange);
            }
        };
    }

}
