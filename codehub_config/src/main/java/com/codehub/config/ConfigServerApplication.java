package com.codehub.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/27
 *
 * 配置中心微服务
 */
@EnableEurekaClient  //Eureka客户端注解
@SpringBootApplication
@EnableConfigServer  //开启配置服务
public class ConfigServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }
}
