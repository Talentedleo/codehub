package com.codehub.base.controller;

import com.codehub.base.pojo.City;
import com.codehub.base.service.CityService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author Leo Lee
 * @Date 19/4/8
 * @Version 1.0
 *
 * 城市的controller
 */
@RestController  //返回json格式
@RequestMapping("/city")
//@CrossOrigin
public class CityController {

    @Autowired
    private CityService cityService;

    /** 查询所有 */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK, "查询成功", cityService.findAll());
    }

    /** 根据id查询 */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
        return new Result(true, StatusCode.OK, "查询成功", cityService.findById(id));
    }

    /** 添加 */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody City city){ //页面post请求传过来的json格式数据
        cityService.add(city);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    /** 修改 */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody City city, @PathVariable String id){
        // 请求体中可能没有id,所以这样设置一下id
        city.setId(id);
        cityService.update(city);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /** 根据id删除 */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result deleteById(@PathVariable String id){
        cityService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /** 标签条件搜索 */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map<String, Object> searchMap){
        List<City> list = cityService.findSearch(searchMap);
        return new Result(true, StatusCode.OK, "查询成功", list);
    }

    /** 标签分页+条件 */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map<String, Object> searchMap, @PathVariable int page, @PathVariable int size){
        Page<City> pageData = cityService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }
}
