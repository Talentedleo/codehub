package com.codehub.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/24
 *
 * Eureka注册中心微服务
 */
@SpringBootApplication
@EnableEurekaServer  //开启Eureka服务
public class EurekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}
