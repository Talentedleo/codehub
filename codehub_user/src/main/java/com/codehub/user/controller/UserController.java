package com.codehub.user.controller;

import com.codehub.user.pojo.User;
import com.codehub.user.service.UserService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 控制器层
 * @author Weiping Li
 *
 */
@RestController
//@CrossOrigin
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",userService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",userService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<User> pageList = userService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<User>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",userService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param user
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody User user  ){
		userService.add(user);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param user
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody User user, @PathVariable String id ){
		user.setId(id);
		userService.update(user);		
		return new Result(true,StatusCode.OK,"修改成功");
	}

	//获取request
	@Autowired
	private HttpServletRequest request;

	//jwt工具
	@Autowired
	private JwtUtil jwtUtil;
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id){
	/*	//2.1 使用jjwt解析token字符串,是否合法,是否过期
		//1)从request获取Authorization的头信息,客户端浏览器将token放在header里面带过来比较安全,如果是https协议,头信息会加密,会更安全
		String auth = request.getHeader("Authorization");

		if (auth == null){
			return new Result(false, StatusCode.ACCESS_ERROR, "你无权访问");
		}

		//请求头以Bearer 开头,这里是自定义的,为了增加安全性
		if (!auth.startsWith("Bearer ")){
			return new Result(false, StatusCode.ACCESS_ERROR, "你无权访问");
		}

		//2)取出token字符串, Bearer 空格总共有7位
		String token = auth.substring(7);

		//3)解析jwt字符串合法性
		Claims claims = jwtUtil.parseJWT(token);

		if (claims == null){
			return new Result(false, StatusCode.ACCESS_ERROR, "你无权访问");
		}

		//2.2 从载荷中取出role,如果role是管理员,才能删除用户
		if (!claims.get("roles").equals("admin")){
			return new Result(false, StatusCode.ACCESS_ERROR, "你无权访问");
		}*/

		// 拦截器给request域中设置了管理员的值,从request域中获取对应的载荷体里面的值
		Claims claims = (Claims) request.getAttribute("admin_claims");
		if (claims == null){
			return new Result(false, StatusCode.ACCESS_ERROR, "你不是管理员,无权访问");
		}

		userService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 获取手机验证码
	 */
	@RequestMapping(value = "/sendsms/{mobile}", method = RequestMethod.POST)
	public Result sendsms(@PathVariable String mobile){
		userService.sendsms(mobile);
		return new Result(true, StatusCode.OK, "发送手机验证码成功");
	}

	/**
	 * 用户注册
	 */
	@RequestMapping(value = "/register/{code}", method = RequestMethod.POST)
	public Result register(@PathVariable String code, @RequestBody User user){
		boolean flag = userService.register(code, user);
		if (flag == false){
			//注册失败,验证码不一致
			return new Result(false, StatusCode.ERROR, "你输入的验证码有误");
		}

		return new Result(true, StatusCode.OK, "注册成功");
	}

	/**
	 * 用户登录
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result login(@RequestBody User user){
		User loginUser = userService.login(user);
		if (loginUser == null){
			//登录失败
			return new Result(false, StatusCode.USER_PASS_ERROR, "用户名或密码输入有误");
		}else {
			//生成用户的Token信息
			String token = jwtUtil.createJWT(loginUser.getId(), loginUser.getMobile(), "user");

			Map map = new HashMap();
			map.put("name", loginUser.getMobile());
			map.put("token", token);

			//登录成功
			return new Result(true, StatusCode.OK, "登录成功", map);
		}
	}

	/** 更新关注数,x是更新的数量,正负都可以 */
	@RequestMapping(value = "/updateFollowcount/{userid}/{x}", method = RequestMethod.PUT)
	public Result updateFollowcount(@PathVariable String userid, @PathVariable int x){
		userService.updateFollowcount(userid, x);
		return new Result(true, StatusCode.OK, "更新关注数成功");
	}

	/** 更新粉丝数,x是更新的数量,正负都可以 */
	@RequestMapping(value = "/updateFanscount/{userid}/{x}", method = RequestMethod.PUT)
	public Result updateFanscount(@PathVariable String userid, @PathVariable int x){
		userService.updateFanscount(userid, x);
		return new Result(true, StatusCode.OK, "更新粉丝数成功");
	}
}
