package com.codehub.user.dao;

import com.codehub.user.pojo.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 数据访问接口
 * @author Weiping Li
 *
 */
public interface AdminDao extends JpaRepository<Admin,String>,JpaSpecificationExecutor<Admin>{

    /** 根据loginname查询管理员 */
    Admin findByLoginname(String loginname);
}
