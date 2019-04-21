package com.codehub.web;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/27
 *
 * 前台网关过滤器
 */
@Component
public class WebFilter extends ZuulFilter {

    /**
     * 过滤器类型: 代表过滤器执行位置
     *  pre: 在进入网关之前
     *  route: 在执行网关的时候
     *  post: 在执行完成网关之后
     *  error: 在执行网关错误的时候
     * @return
     */
    @Override
    public String filterType() {
        //前置过滤器
        return "pre";
    }

    /**
     * 过滤器执行顺序
     * @return
     */
    @Override
    public int filterOrder() {
        //优先级为0,数字越大,优先级越低
        return 0;
    }

    /**
     * 是否让过滤器生效
     *  true: 生效
     *  false: 失效
     * @return
     */
    @Override
    public boolean shouldFilter() {
        //是否执行该过滤器,此处为true,说明需要过滤
        return true;
    }

    /**
     * 执行过滤器逻辑方法
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        //System.out.println("zuul过滤器...");

        //1.从用户的请求获取Authorization信息
        RequestContext currentContext = RequestContext.getCurrentContext();

        HttpServletRequest request = currentContext.getRequest();
        String auth = request.getHeader("Authorization");

        //2.把Authorization信息加入网关的请求中
        if (auth != null && !auth.equals("")){
            currentContext.addZuulRequestHeader("Authorization", auth);
        }

        //return null: 代表放行请求
        return null;
    }
}
