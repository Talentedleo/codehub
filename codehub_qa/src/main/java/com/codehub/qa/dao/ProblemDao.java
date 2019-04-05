package com.codehub.qa.dao;

import com.codehub.qa.pojo.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Weiping Li
 *
 */
public interface ProblemDao extends JpaRepository<Problem,String>,JpaSpecificationExecutor<Problem>{

    /** 根据labelid查询最新问答 */
    @Query("select p from Problem p where p.id in (select pl.problemid from Pl pl where pl.labelid = ?1) order by p.replytime desc")
    Page<Problem> findByLabelid(String labelid, Pageable pageable);

    /** 根据labelid查询热门问答 */
    @Query("select p from Problem p where p.id in (select pl.problemid from Pl pl where pl.labelid = ?1) order by p.reply desc")
    Page<Problem> findByHotListLabelid(String labelid, Pageable pageable);

    /** 根据labelid查询等待问答 */
    @Query("select p from Problem p where p.id in (select pl.problemid from Pl pl where pl.labelid = ?1) and p.reply = 0")
    Page<Problem> findByWaitListLabelid(String labelid, Pageable pageable);
}
