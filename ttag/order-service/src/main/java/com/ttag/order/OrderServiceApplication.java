package com.ttag.order;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients(basePackages = "com.ttag.api.client")
@SpringBootApplication
public class OrderServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    /**
     * 添加消息Id
     *
     * @return 消息转换器
     */
    @Bean
    public MessageConverter jacksonMessageConvertor(){
        Jackson2JsonMessageConverter jjmc = new Jackson2JsonMessageConverter();
        jjmc.setCreateMessageIds(true);
        return jjmc;
    }
}
