package com.codehub.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/26
 *
 * 前台网站网关
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy  //开启Zuul转发
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }
}
