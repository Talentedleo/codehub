package com.codehub.article.dao;

import com.codehub.article.pojo.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Weiping Li
 *
 */
public interface ArticleDao extends JpaRepository<Article,String>,JpaSpecificationExecutor<Article>{

    /** 文章审核 */
    @Query("update Article a set a.state = '1' where a.id = ?1")
    @Modifying  //如果是修改操作,必须带上@Modifying
    public void examine(String id);

    /** 文章点赞 */
    @Query("update Article a set a.thumbup = a.thumbup + 1 where a.id = ?1")
    @Modifying  //如果是修改操作,必须带上@Modifying
    public void thumbup(String id);
}
