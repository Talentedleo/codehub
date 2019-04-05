package com.codehub.base.controller;

import entity.Result;
import entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/18
 *
 * 全局异常处理类
 */
@ControllerAdvice
public class BaseExceptionHandler {

    /**
     * 定义异常处理方法
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody  //这里必须加上@ResponseBody
    public Result handlerError(Exception e){
        return new Result(false, StatusCode.ERROR, "出现了异常: "+e.getMessage());
    }
}
