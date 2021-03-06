package com.codehub.friend.pojo;

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
 * File Created on 2018/11/25
 *
 * 好友实体
 */
@Getter
@Setter
@Entity
@Table(name = "tb_friend")
@IdClass(Friend.class)
public class Friend implements Serializable{

    @Id
    private String userid;  //当前用户id

    @Id
    private String friendid;  //好友id

    private String islike;  //单双向喜欢  0:单向  1:双向

}
