package com.codehub.qa.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/18
 *
 * 问答标签中间表实体类
 */
@Getter
@Setter
@Entity
@Table(name = "tb_pl")
@IdClass(Pl.class)  //主键所在的类,id是可以分开类写的
public class Pl implements Serializable{

    //联合主键

    @Id
    private String problemid;  //问答id

    @Id
    private String labelid;  //标签id

}
