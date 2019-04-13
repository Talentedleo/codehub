package com.codehub.base.service;

import com.codehub.base.dao.CityDao;
import com.codehub.base.pojo.City;
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
 * @Author Leo Lee
 * @Date 19/4/12
 * @Version 1.0
 */
@Service
public class CityService {

    @Autowired
    private CityDao cityDao;

    @Autowired
    private IdWorker idWorker;

    /** 查询所有 */
    public List<City> findAll(){
        return cityDao.findAll();
    }

    /** 查询一个 */
    public City findById(String id){
        return cityDao.findById(id).get();
    }

    /** 添加 */
    public void add(City City){
        //获取分布式ID
        City.setId(idWorker.nextId()+"");
        cityDao.save(City);
    }

    /** 修改 */
    public void update(City City){
        cityDao.save(City);
    }

    /** 删除 */
    public void deleteById(String id){
        cityDao.deleteById(id);
    }

    /** 创建Specification对象 */
    private Specification<City> createSpecification(Map<String, Object> searchMap){
        return new Specification<City>() {
            @Override
            public Predicate toPredicate(Root<City> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                //设计集合存储所有条件
                List<Predicate> predicateList = new ArrayList<>();

                //判断非空
                if(searchMap.get("name") != null && !searchMap.get("name").equals("")){
                    // name like '%xxx%'
                    predicateList.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + searchMap.get("Cityname") + "%"));
                }

                //判断非空
                if(searchMap.get("ishot") != null && !searchMap.get("ishot").equals("")){
                    // ishot = 'xxx'
                    predicateList.add(criteriaBuilder.equal(root.get("ishot").as(String.class), searchMap.get("state")));
                }

                // 创建带类型的数组
                Predicate[] preArray = new Predicate[predicateList.size()];
                // 将list转换成数组,拼接参数
                // name like '%xxxx%' and  ishot = 'xxxx'
                return criteriaBuilder.and(predicateList.toArray(preArray));
            }
        };
    }

    /** 条件搜索 */
    public List<City> findSearch(Map<String, Object> searchMap) {
        // 1.创建Specification对象
        Specification<City> spec = createSpecification(searchMap);
        // 2.条件查询
        return cityDao.findAll(spec);
    }

    /** 分页+条件搜索 */
    public Page<City> findSearch(Map<String, Object> searchMap, int page, int size) {
        // 1.创建Specification对象
        Specification<City> spec = createSpecification(searchMap);
        // 2.条件查询
        //PageRequest.of(page,size): 返回Pageable的接口实现类 , page从0开始的
        return cityDao.findAll(spec, PageRequest.of(page - 1, size));
    }
}
