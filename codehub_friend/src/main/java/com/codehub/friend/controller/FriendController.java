package com.codehub.friend.controller;

import com.codehub.friend.service.FriendService;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/25
 *
 * 好友controller
 */
@RestController
@RequestMapping("/friend")
@CrossOrigin
public class FriendController {

    @Autowired
    private FriendService friendService;

    @Autowired
    private HttpServletRequest request;

    /** 添加好友 */
    @RequestMapping(value = "/like/{friendid}/{type}", method = RequestMethod.PUT)
    public Result addFriend(@PathVariable String friendid, @PathVariable String type){
        //校验用户合法性
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (claims == null){
            return new Result(false, StatusCode.ACCESS_ERROR, "你没有登录,请先登录");
        }
        //从token里面取出userid(获取当前登录用户id)
        String userid = claims.getId();

        //判断type类型
        if (type.equals("1")){
            //添加好友
            Integer flag = friendService.addFriend(userid, friendid);
            if (flag == 0){
                //已经添加过该好友
                return new Result(false, StatusCode.REPEAT_ERROR, "你已经添加过该好友");
            }else {
                //成功添加了好友
                return new Result(true, StatusCode.OK, "添加好友成功");
            }
        }else {
            //添加黑名单(非好友)
            friendService.addNoFriend(userid, friendid);

            return new Result(false, StatusCode.OK, "添加非好友成功");
        }
    }

    /** 删除好友 */
    @RequestMapping(value = "/{friendid}", method = RequestMethod.DELETE)
    public Result deleteFriend(@PathVariable String friendid){
        //校验用户合法性
        Claims claims = (Claims) request.getAttribute("user_claims");
        if (claims == null){
            return new Result(false, StatusCode.ACCESS_ERROR, "你没有登录,请先登录");
        }
        //从Token里面取出userid(获取当前登录用户ID)
        String userid = claims.getId();

        friendService.deleteFriend(userid, friendid);

        return new Result(true, StatusCode.OK, "删除好友成功");
    }
}
