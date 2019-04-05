package com.codehub.spit.controller;

import com.codehub.spit.pojo.Spit;
import com.codehub.spit.service.SpitService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/21
 *
 * 吐槽controller
 */
@RestController
//@CrossOrigin
@RequestMapping("/spit")
public class SpitController {

    @Autowired
    private SpitService spitService;

    /** 查询全部数据 */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll(){
        return new Result(true, StatusCode.OK, "查询成功", spitService.findAll());
    }

    /** 根据ID查询 */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id){
        return new Result(true, StatusCode.OK, "查询成功", spitService.findById(id));
    }

    /** 增加 */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Spit spit){
        spitService.add(spit);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /** 修改 */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Spit spit, @PathVariable String id){
        spit.setId(id);
        spitService.update(spit);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /** 删除 */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id){
        spitService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /** 根据parentId查询吐槽评论 */
    @RequestMapping(value = "/comment/{parentid}/{page}/{size}", method = RequestMethod.GET)
    public Result comment(@PathVariable String parentid, @PathVariable int page, @PathVariable int size){
        Page<Spit> pageData = spitService.comment(parentid, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }

    @Autowired
    private RedisTemplate redisTemplate;

    /** 吐槽点赞 */
    @RequestMapping(value = "/thumbup/{spitId}", method = RequestMethod.PUT)
    public Result thumbup(@PathVariable String spitId){
        String userid = "1001"; //临时模拟当前登录用户id

        //1.从redis查询该用户是否对该吐槽点赞过
        String flag = (String) redisTemplate.opsForValue().get("spit_thumbup_" + userid + "_" + spitId);

        if (flag != null){
            //点赞过了
            //return new Result(false, StatusCode.REPEAT_ERROR, "你已经点赞过了");

            //取消点赞
            spitService.cancelThumbup(spitId);

            //清楚redis的点赞记录
            redisTemplate.delete("spit_thumbup_" + userid + "_" + spitId);

            return new Result(true, StatusCode.OK, "取消点赞成功");
        }else{
            spitService.thumbup(spitId);

            //2.把用户点赞记录保存到redis,过期时间为5分钟
            redisTemplate.opsForValue().set("spit_thumbup_" + userid + "_" + spitId, "1", 5, TimeUnit.MINUTES);

            return new Result(true, StatusCode.OK, "点赞成功");
        }

    }

}
