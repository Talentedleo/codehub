package com.codehub.base.controller;


import com.codehub.base.pojo.Label;
import com.codehub.base.service.LabelService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/17
 *
 * 标签的controller
 */
@RestController  //返回json格式
@RequestMapping("/label")
@CrossOrigin  //支持跨域请求
public class LabelController {

    @Autowired
    private LabelService labelService;

    /** 查询所有 */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK, "查询成功", labelService.findAll());
    }

    /** 根据id查询 */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id){

        /** 测试服务调用Feign里面的负载均衡Ribbon */
        System.out.println("NO2----" + id);

        return new Result(true, StatusCode.OK, "查询成功", labelService.findById(id));
    }

    /** 添加 */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Label label){ //页面post请求传过来的json格式数据
        labelService.add(label);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /** 修改 */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Label label, @PathVariable String id){
        // 请求体中可能没有id,所以这样设置一下id
        label.setId(id);
        labelService.update(label);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /** 根据id删除 */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id){
        labelService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /** 标签条件搜索 */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map<String, Object> searchMap){
        List<Label> list = labelService.findSearch(searchMap);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    /** 标签分页+条件 */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map<String, Object> searchMap, @PathVariable int page, @PathVariable int size){
        Page<Label> pageData = labelService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }
}
