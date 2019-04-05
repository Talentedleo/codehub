package com.codehub.base.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/27
 *
 * 测试Bus总线自动更新配置
 */
@RestController
@RefreshScope  //该注解用于刷新配置
public class TestController {

    @Value("${sms.ip}")
    private String ip;

    @RequestMapping(value = "/ip", method = RequestMethod.GET)
    public String ip() {
        return ip;
    }
}
