package com.codehub.search.controller;

import com.codehub.search.pojo.Article;
import com.codehub.search.service.ArticleService;
import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/22
 *
 * 文章controller
 */
@RestController
@RequestMapping("/article")
@CrossOrigin
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 文章搜索
     */
    @RequestMapping(value = "/search/{keyword}/{page}/{size}", method = RequestMethod.GET)
    public Result search(@PathVariable String keyword, @PathVariable int page, @PathVariable int size){
        Page<Article> pageData = articleService.search(keyword, page, size);
        return new Result(true, StatusCode.OK, "搜索成功", new PageResult<>(pageData.getTotalElements(), pageData.getContent()));
    }
}
