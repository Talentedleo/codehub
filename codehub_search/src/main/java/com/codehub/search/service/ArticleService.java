package com.codehub.search.service;

import com.codehub.search.dao.ArticleDao;
import com.codehub.search.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/22
 */
@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    /** 查询方法 */
    public Page<Article> search(String keyword, int page, int size) {
        return articleDao.findByTitleOrContentLike(keyword, keyword, PageRequest.of(page - 1, size));
    }
}
