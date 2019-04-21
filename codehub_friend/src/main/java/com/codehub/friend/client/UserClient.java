package com.codehub.friend.client;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/26
 *
 * 远程调用用户模块接口
 */
@FeignClient("user")
public interface UserClient {

    /** 更新关注数,x是更新的数量,正负都可以 */
    @RequestMapping(value = "/user/updateFollowcount/{userid}/{x}", method = RequestMethod.PUT)
    public Result updateFollowcount(@PathVariable("userid") String userid, @PathVariable("x") int x);

    /** 更新粉丝数,x是更新的数量,正负都可以 */
    @RequestMapping(value = "/user/updateFanscount/{userid}/{x}", method = RequestMethod.PUT)
    public Result updateFanscount(@PathVariable("userid") String userid, @PathVariable("x") int x);
}
