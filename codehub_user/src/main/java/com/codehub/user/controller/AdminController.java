package com.codehub.user.controller;

import com.codehub.user.pojo.Admin;
import com.codehub.user.service.AdminService;
import com.netflix.discovery.converters.Auto;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import io.jsonwebtoken.Claims;
import org.apache.http.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import util.JwtUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器层
 * @author Weiping Li
 *
 */
@RestController
//@CrossOrigin
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true,StatusCode.OK,"查询成功",adminService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findById(@PathVariable String id){
		return new Result(true,StatusCode.OK,"查询成功",adminService.findById(id));
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
		Page<Admin> pageList = adminService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Admin>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",adminService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param admin
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Admin admin  ){
		adminService.add(admin);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param admin
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Admin admin, @PathVariable String id ){
		admin.setId(id);
		adminService.update(admin);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		adminService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	/** jwt工具对象 */
	@Autowired
	private JwtUtil jwtUtil;

	/**
	 * 管理员登录
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Result login(@RequestBody Admin admin){
		Admin loginAdmin = adminService.login(admin);

		if (loginAdmin == null){
			//登录失败
			return new Result(false, StatusCode.USER_PASS_ERROR, "用户或密码错误");
		}else {
			//1 利用jjwt生成token加密字符串(往载荷加入role角色)
			String token = jwtUtil.createJWT(loginAdmin.getId(), loginAdmin.getLoginname(), "admin");

			//2. 直接把token字符串返回前端, 使用map封装,方便前端取出数据
			Map data = new HashMap();
			data.put("name", loginAdmin.getLoginname());
			data.put("token", token);

			//登录成功
			return new Result(true, StatusCode.OK, "登录成功", data);
		}
	}

	/**
	 * 获取管理员信息
	 */
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public Result info(@RequestParam String token){
		//校验token合法性
		Claims claims = jwtUtil.parseJWT(token);

		if (claims != null){
			String id = claims.getId();
			// 根据荷载中获取的id查找管理员
			Admin adminInfo = adminService.findById(id);
			List roleList = new ArrayList<>();
			roleList.add("admin");

			//封装返回数据
			Map data = new HashMap();
			data.put("name", adminInfo.getLoginname());
			data.put("avatar", adminInfo.getAvatar());
			data.put("role", roleList);
			data.put("roles", roleList);
			return new Result(true, StatusCode.OK, "获取用户信息成功", data);

		}else {
			return new Result(false, StatusCode.ACCESS_ERROR, "获取用户信息失败");
		}

	}
	
}
