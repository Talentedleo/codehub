package com.codehub.base.dao;


import com.codehub.base.pojo.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/17
 *
 * 标签dao
 */
public interface LabelDao extends JpaRepository<Label, String>, JpaSpecificationExecutor<Label> {
}
