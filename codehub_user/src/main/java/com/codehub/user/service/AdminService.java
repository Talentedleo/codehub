package com.codehub.user.service;

import com.codehub.user.dao.AdminDao;
import com.codehub.user.pojo.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
 * 服务层
 * 
 * @author Weiping Li
 *
 */
@Service
public class AdminService {

	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Admin> findAll() {
		return adminDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Admin> findSearch(Map whereMap, int page, int size) {
		Specification<Admin> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return adminDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Admin> findSearch(Map whereMap) {
		Specification<Admin> specification = createSpecification(whereMap);
		return adminDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Admin findById(String id) {
		return adminDao.findById(id).get();
	}

	// 加密工具
	@Autowired
	private BCryptPasswordEncoder encoder;

	/**
	 * 增加
	 * @param admin
	 */
	public void add(Admin admin) {
		admin.setId( idWorker.nextId()+"" );

		//对管理员密码进行加密
		admin.setPassword(encoder.encode(admin.getPassword()));

		adminDao.save(admin);
	}

	/**
	 * 修改
	 * @param admin
	 */
	public void update(Admin admin) {
		adminDao.save(admin);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		adminDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Admin> createSpecification(Map searchMap) {

		return new Specification<Admin>() {

			@Override
			public Predicate toPredicate(Root<Admin> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 登陆名称
                if (searchMap.get("loginname")!=null && !"".equals(searchMap.get("loginname"))) {
                	predicateList.add(cb.like(root.get("loginname").as(String.class), "%"+(String)searchMap.get("loginname")+"%"));
                }
                // 密码
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                	predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // 状态
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

	/**
	 * 登录
	 * @param admin
	 * @return
	 */
	public Admin login(Admin admin) {
		//1.先判断loginname是否存在
		Admin loginAdmin = adminDao.findByLoginname(admin.getLoginname());

		//2.如果loginname存在,判断密码是否一致
		//判断密码一致的思路:把用户输入的密码加密成BCrypt密码,再和数据库加密的密码进行对比
		/**
		 * matches方法的参数
		 * 		参数一: 原密码
		 * 		参数二: 数据库加密后的密码
		 *
		 * 	matches匹配思路:
		 * 		1.工具从数据库的加密后的密码里面提取出当初到的salt值
		 * 		2.使用用户输入的密码 和 salt值进行加密运算,得到加密后的密码
		 * 		3.使用上一步加密的密码, 和 数据库加密后的密码进行对比
		 * 		4.如果一致的话,返回true,否则,返回false
		 */
		if (loginAdmin != null && !encoder.matches(admin.getPassword(), loginAdmin.getPassword())){
			//密码不一致
			return null;
		}else {
			//登录成功
			return loginAdmin;
		}
	}
}
