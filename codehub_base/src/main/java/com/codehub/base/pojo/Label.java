package com.codehub.base.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/17
 *
 * 标签实体类
 */
@Getter
@Setter
@Entity
@Table(name = "tb_label")
public class Label implements Serializable {

    @Id     //注意:这里的id是程序的分布式id生成器分配的,不需要指定策略
    private String id;//编号
    private String labelname;//标签名称
    private String state;//状态
    private Long count;//使用数量
    private String recommend;//是否推荐
    private Long fans;//粉丝数量

}
