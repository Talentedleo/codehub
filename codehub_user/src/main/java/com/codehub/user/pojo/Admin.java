package com.codehub.user.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 实体类
 * @author Weiping Li
 *
 */
@Getter
@Setter
@Entity
@Table(name="tb_admin")
public class Admin implements Serializable{

	@Id
	private String id;//ID

	private String loginname;//登陆名称
	private String password;//密码
	private String avatar;//管理员头像url地址
	private String state;//状态

}
