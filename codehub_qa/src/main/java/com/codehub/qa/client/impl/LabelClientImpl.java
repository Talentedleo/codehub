package com.codehub.qa.client.impl;

import com.codehub.qa.client.LabelClient;
import entity.Result;
import entity.StatusCode;
import org.springframework.stereotype.Component;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/26
 *
 * Feign下的熔断器
 */
@Component
public class LabelClientImpl implements LabelClient {

    @Override
    public Result findById(String id) {
        return new Result(false, StatusCode.REMOTE_ERROR, "熔断器启动了");
    }
}
