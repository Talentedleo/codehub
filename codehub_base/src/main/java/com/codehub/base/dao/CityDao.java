package com.codehub.base.dao;

import com.codehub.base.pojo.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @Author Leo Lee
 * @Date 19/4/12
 * @Version 1.0
 */
public interface CityDao extends JpaRepository<City, String>, JpaSpecificationExecutor<City> {
}
