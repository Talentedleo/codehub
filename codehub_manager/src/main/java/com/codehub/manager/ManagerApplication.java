package com.codehub.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import util.JwtUtil;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/26
 *
 * 管理员后台网站网关
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy  // 开启Zuul转发
public class ManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class, args);
    }

    @Bean
    public JwtUtil jwtUtil(){
        return new JwtUtil();
    }
}
