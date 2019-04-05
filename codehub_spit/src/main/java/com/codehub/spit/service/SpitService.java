package com.codehub.spit.service;

import com.codehub.spit.dao.SpitDao;
import com.codehub.spit.pojo.Spit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import util.IdWorker;

import java.util.List;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/21
 *
 * 吐槽service
 */
@Service
public class SpitService {

    @Autowired
    private SpitDao spitDao;

    @Autowired
    private IdWorker idWorker;

    /** 查询所有 */
    public List<Spit> findAll(){
        return spitDao.findAll();
    }

    /** 根据id查询 */
    public Spit findById(String id){
        return spitDao.findById(id).get();
    }

    /** 添加 */
    public void add(Spit spit){
        spit.setId(idWorker.nextId() + "");
        spitDao.save(spit);

        //如果该记录是吐槽的评论,则该评论对应的吐槽的回复数+1
        if (spit.getParentid() != null && !spit.getParentid().equals("")){
            //该评论对应的吐槽的回复数+1
            Query query = new Query();
            query.addCriteria(Criteria.where("_id").is(spit.getParentid()));

            Update update = new Update();
            update.inc("comment", 1);

            mongoTemplate.updateFirst(query, update, "spit");
        }

    }

    /** 修改 */
    public void update(Spit spit){
        spitDao.save(spit);
    }

    /** 删除 */
    public void deleteById(String id){
        spitDao.deleteById(id);
    }

    /** 分页查询吐槽的评论 */
    public Page<Spit> comment(String parentid, int page, int size) {
        // PageRequest是Pageable接口的实现
        return spitDao.findByParentid(parentid, PageRequest.of(page, size));
    }

    /** 吐槽点赞:第一种方案 */
    /*public void thumbup(String spitid) {
        Spit spit = findById(spitid);
        spit.setThumbup(spit.getThumbup() + 1);
        update(spit);
    }*/

    @Autowired
    private MongoTemplate mongoTemplate;

    /** 吐槽点赞:第二种方案 */
    public void thumbup(String spitid) {
        // db.spit.update({"_id":"1065068496616034304"},{$inc:{"thumbup":NumberInt(1)}})

        //1.创建条件对象
        Query query = new Query();
        //{"_id":"1065068496616034304"}
        query.addCriteria(Criteria.where("_id").is(spitid));

        //2.创建更新对象
        Update update = new Update();
        //{$inc:{"thumbup":NumberInt(1)}}
        update.inc("thumbup", 1);

        //3.执行更新
        // db.spit.update({"_id":"1065068496616034304"},{$inc:{"thumbup":NumberInt(1)}})
        mongoTemplate.updateFirst(query, update, "spit");
    }

    /** 取消点赞 */
    public void cancelThumbup(String spitid) {

        //1.创建条件对象
        Query query = new Query();
        //{"_id":"1065068496616034304"}
        query.addCriteria(Criteria.where("_id").is(spitid));

        //2.创建更新对象
        Update update = new Update();
        //{$inc:{"thumbup":NumberInt(1)}}
        update.inc("thumbup", -1);

        //3.执行更新
        // db.spit.update({"_id":"1065068496616034304"},{$inc:{"thumbup":NumberInt(1)}})
        mongoTemplate.updateFirst(query, update, "spit");
    }
}
