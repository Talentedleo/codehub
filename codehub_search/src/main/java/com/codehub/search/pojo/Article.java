package com.codehub.search.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;

/**
 * 文章实体类
 * <p>
 * type: 文档所在类型
 * indexName: 类型所在的索引
 */
@Getter
@Setter
@Document(type = "article", indexName = "codehub")
public class Article implements Serializable {

    @Id  // 注意:这个id字段映射ElasticSearch的文档的_id
    private String id;//ID

    private String columnid;//专栏ID
    private String userid;//用户ID
    private String title;//标题
    private String content;//文章正文
    private String image;//文章封面
    private java.util.Date createtime;//发表日期
    private java.util.Date updatetime;//修改日期
    private String ispublic;//是否公开
    private String istop;//是否置顶
    private Integer visits;//浏览量
    private Integer thumbup;//点赞数
    private Integer comment;//评论数
    private String state;//审核状态
    private String channelid;//所属频道
    private String url;//URL
    private String type;//类型


}
