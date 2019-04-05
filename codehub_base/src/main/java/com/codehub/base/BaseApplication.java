package com.codehub.base;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/17
 *
 * 基础服务
 */
@SpringBootApplication
@EnableEurekaClient  //Eureka客户端注解
public class BaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(BaseApplication.class,args);
    }

    /**
     * 初始化分布式ID生成器
     * @return
     */
    @Bean
    public IdWorker idWorker(){
        return new IdWorker();
    }
}
