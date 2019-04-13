package com.codehub.base.service;


import com.codehub.base.dao.LabelDao;
import com.codehub.base.pojo.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/17
 *
 * 标签service
 * 通过controller来相互调用的,所以service可以没有接口
 */
@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;

    /** 查询所有 */
    public List<Label> findAll(){
        return labelDao.findAll();
    }

    /** 查询一个 */
    public Label findById(String id){
        return labelDao.findById(id).get();
    }

    /** 添加 */
    public void add(Label label){
        //获取分布式ID
        label.setId(idWorker.nextId()+"");
        labelDao.save(label);
    }

    /** 修改 */
    public void update(Label label){
        labelDao.save(label);
    }

    /** 删除 */
    public void deleteById(String id){
        labelDao.deleteById(id);
    }

    /** 创建Specification对象 */
    private Specification<Label> createSpecification(Map<String, Object> searchMap){
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //设计集合存储所有条件
                List<Predicate> predicateList = new ArrayList<>();

                //判断非空
                if(searchMap.get("labelname") != null && !searchMap.get("labelname").equals("")){
                    // labelname like '%xxx%'
                    predicateList.add(criteriaBuilder.like(root.get("labelname").as(String.class), "%" + searchMap.get("labelname") + "%"));
                }

                //判断非空
                if(searchMap.get("state") != null && !searchMap.get("state").equals("")){
                    // labelname = 'xxx'
                    predicateList.add(criteriaBuilder.equal(root.get("state").as(String.class), searchMap.get("state")));
                }

                //判断非空
                if(searchMap.get("recommend") != null && !searchMap.get("recommend").equals("")){
                    // recommend = 'xxx'
                    predicateList.add(criteriaBuilder.equal(root.get("recommend").as(String.class), searchMap.get("recommend")));
                }

                // 创建带类型的数组
                Predicate[] preArray = new Predicate[predicateList.size()];
                // 将list转换成数组,拼接参数
                // labelname like '%xxxx%' and  state = 'xxxx' and  recommend = 'xxxx'
                return criteriaBuilder.and(predicateList.toArray(preArray));
            }
        };
    }

    /** 条件搜索 */
    public List<Label> findSearch(Map<String, Object> searchMap) {
        // 1.创建Specification对象
        Specification<Label> spec = createSpecification(searchMap);
        // 2.条件查询
        return labelDao.findAll(spec);
    }

    /** 分页+条件搜索 */
    public Page<Label> findSearch(Map<String, Object> searchMap, int page, int size) {
        // 1.创建Specification对象
        Specification<Label> spec = createSpecification(searchMap);
        // 2.条件查询
        //PageRequest.of(page,size): 返回Pageable的接口实现类 , page从0开始的
        return labelDao.findAll(spec, PageRequest.of(page - 1, size));
    }
}
