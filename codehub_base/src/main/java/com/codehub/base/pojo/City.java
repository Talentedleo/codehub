package com.codehub.base.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author Leo Lee
 * @Date 19/4/12
 * @Version 1.0
 *
 * 城市实体类
 */
@Getter
@Setter
@Entity
@Table(name = "tb_city")
public class City implements Serializable {

    @Id     //注意:这里的id是程序的分布式id生成器分配的,不需要指定策略
    private String id;//编号

    private String name;//名字
    private String ishot;//是否热门
}
