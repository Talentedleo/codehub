package com.codehub.user;

import com.codehub.user.filter.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/24
 *
 * 添加拦截器到环境中
 */
@Configuration
public class ApplicationConfig extends WebMvcConfigurationSupport{

    @Autowired
    private JwtFilter jwtFilter;

    /** 拦截所有,但是放行login的请求 */
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtFilter)
                .addPathPatterns("/**")
                .excludePathPatterns("/**/login");
    }
}
