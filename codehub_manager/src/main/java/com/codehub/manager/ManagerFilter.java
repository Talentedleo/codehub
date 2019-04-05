package com.codehub.manager;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/27
 *
 * 管理员后台网关
 */
@Component
public class ManagerFilter extends ZuulFilter {

    /** 过滤器类型:处理的位置 */
    @Override
    public String filterType() {
        return "pre";
    }

    /** 优先度 */
    @Override
    public int filterOrder() {
        return 0;
    }

    /** 是否启用过滤器 */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 执行过滤器逻辑方法
     */
    @Override
    public Object run() throws ZuulException {
        //1.获取Authorization头
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        String auth = request.getHeader("Authorization");

        //放行管理员登录请求
        String requestURI = request.getRequestURI();
        if (requestURI.contains("/admin/login")){

            //放行
            return null;
        }

        //2.判断auth是否为空
        if (auth != null && !auth.equals("")){

            //3.判断是否以Bearer开头
            if (auth.startsWith("Bearer ")){
                //4.取出token信息
                String token = auth.substring(7);

                //5.校验token合法性
                Claims claims = jwtUtil.parseJWT(token);

                if (claims != null){

                    //6.代表用户已经登录了,判断用户是否为管理员身份
                    if (claims.get("roles").equals("admin")){
                        //放行
                        return null;
                    }
                }
            }
        }

        //当前用户不合法
        //终止请求,发送响应(不放行)
        currentContext.setSendZuulResponse(false);
        //返回内容给前端
        currentContext.setResponseBody("你不是管理员,无权访问");
        //设置内容类型和编码
        currentContext.getResponse().setContentType("text/html;charset=utf-8");

        return null;
    }
}
