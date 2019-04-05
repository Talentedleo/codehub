package com.codehub.recruit.dao;

import com.codehub.recruit.pojo.Recruit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 数据访问接口
 * @author Weiping Li
 *
 */
public interface RecruitDao extends JpaRepository<Recruit,String>,JpaSpecificationExecutor<Recruit>{

    /** 根据条件查询推荐职位, Top4表示前4个,根据创建时间倒序查询 */
    public List<Recruit> findTop4ByStateOrderByCreatetimeDesc(String state);

    /** 根据条件查询最新职位, Top4表示前4个,根据创建时间倒序查询 */
    public List<Recruit> findTop12ByStateNotOrderByCreatetimeDesc(String state);

}
