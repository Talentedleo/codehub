package com.codehub.spit.dao;

import com.codehub.spit.pojo.Spit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/21
 *
 * 吐槽dao
 */
public interface SpitDao extends MongoRepository<Spit, String>{

    /** 根据parentid分页查询信息:命名查询 */
    public Page<Spit> findByParentid(String parentid, Pageable pageable);
}
