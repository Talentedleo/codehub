package com.codehub.qa.client;

import com.codehub.qa.client.impl.LabelClientImpl;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/25
 *
 * 远程调用接口(标签模块)
 */
@FeignClient(value = "codehub-base", fallback = LabelClientImpl.class)  //使用对方服务名称(注意:服务名称不能包含下划线),就是对方的spring.application.name
public interface LabelClient {

    /** 根据id查询 */
    @RequestMapping(value = "/label/{id}", method = RequestMethod.GET)
    //注意@PathVariable里面的名称和路径的参数名称保持一致,不能省略,SpringCloud是根据@PathVariable("id")里面的id来获取参数调用的
    public Result findById(@PathVariable("id") String id);
}
