package com.codehub.search.dao;

import com.codehub.search.pojo.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/22
 *
 * 文章的dao
 *
 * ElasticsearchRepository
 */
public interface ArticleDao extends ElasticsearchRepository<Article, String> {

    /** 根据关键字模糊查询title或者content */
    public Page<Article> findByTitleOrContentLike(String title, String content, Pageable pageable);
}
