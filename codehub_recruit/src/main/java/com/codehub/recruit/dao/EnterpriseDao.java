package com.codehub.recruit.dao;

import com.codehub.recruit.pojo.Enterprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 * @author Weiping Li
 *
 */
public interface EnterpriseDao extends JpaRepository<Enterprise,String>,JpaSpecificationExecutor<Enterprise>{

    /** 命名查询:根据ishot查询 */
    public List<Enterprise> findByIshot(String ishot);
}
