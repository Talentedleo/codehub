package com.codehub.user.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/24
 *
 * jwt校验拦截器
 */
@Component
public class JwtFilter extends HandlerInterceptorAdapter{

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 进入Controller的方法之前
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String auth = request.getHeader("Authorization");

        // 下面的操作时在放行到controller之前,给request域中存对应的载荷
        if (auth != null){
            // 获取加密token字符串
            String token = auth.substring(7);

            // 解析jwt字符串合法性
            Claims claims = jwtUtil.parseJWT(token);

            if (claims != null){

                //代表jwt的token字符串合法的并且没有过期
                if (claims.get("roles").equals("admin")){
                    //当前用户是管理员角色
                    request.setAttribute("admin_claims", claims);
                }

                if (claims.get("roles").equals("user")){
                    //当前用户是用户角色
                    request.setAttribute("user_claims", claims);
                }
            }
        }

        //放行
        return true;
    }
}
