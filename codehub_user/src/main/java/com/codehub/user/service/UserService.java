package com.codehub.user.service;

import com.codehub.user.dao.UserDao;
import com.codehub.user.pojo.User;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import util.IdWorker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 服务层
 * 
 * @author Weiping Li
 *
 */
@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<User> findAll() {
		return userDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<User> findSearch(Map whereMap, int page, int size) {
		Specification<User> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return userDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<User> findSearch(Map whereMap) {
		Specification<User> specification = createSpecification(whereMap);
		return userDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public User findById(String id) {
		return userDao.findById(id).get();
	}

	@Autowired
	private BCryptPasswordEncoder encoder;

	/**
	 * 增加
	 * @param user
	 */
	public void add(User user) {
		user.setId( idWorker.nextId()+"" );

		//密码加密
		user.setPassword(encoder.encode(user.getPassword()));

		userDao.save(user);
	}

	/**
	 * 修改
	 * @param user
	 */
	public void update(User user) {
		userDao.save(user);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		userDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<User> createSpecification(Map searchMap) {

		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 手机号码
                if (searchMap.get("mobile")!=null && !"".equals(searchMap.get("mobile"))) {
                	predicateList.add(cb.like(root.get("mobile").as(String.class), "%"+(String)searchMap.get("mobile")+"%"));
                }
                // 密码
                if (searchMap.get("password")!=null && !"".equals(searchMap.get("password"))) {
                	predicateList.add(cb.like(root.get("password").as(String.class), "%"+(String)searchMap.get("password")+"%"));
                }
                // 昵称
                if (searchMap.get("nickname")!=null && !"".equals(searchMap.get("nickname"))) {
                	predicateList.add(cb.like(root.get("nickname").as(String.class), "%"+(String)searchMap.get("nickname")+"%"));
                }
                // 性别
                if (searchMap.get("sex")!=null && !"".equals(searchMap.get("sex"))) {
                	predicateList.add(cb.like(root.get("sex").as(String.class), "%"+(String)searchMap.get("sex")+"%"));
                }
                // 头像
                if (searchMap.get("avatar")!=null && !"".equals(searchMap.get("avatar"))) {
                	predicateList.add(cb.like(root.get("avatar").as(String.class), "%"+(String)searchMap.get("avatar")+"%"));
                }
                // E-Mail
                if (searchMap.get("email")!=null && !"".equals(searchMap.get("email"))) {
                	predicateList.add(cb.like(root.get("email").as(String.class), "%"+(String)searchMap.get("email")+"%"));
                }
                // 兴趣
                if (searchMap.get("interest")!=null && !"".equals(searchMap.get("interest"))) {
                	predicateList.add(cb.like(root.get("interest").as(String.class), "%"+(String)searchMap.get("interest")+"%"));
                }
                // 个性
                if (searchMap.get("personality")!=null && !"".equals(searchMap.get("personality"))) {
                	predicateList.add(cb.like(root.get("personality").as(String.class), "%"+(String)searchMap.get("personality")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private RabbitMessagingTemplate rabbitMessagingTemplate;

	/**
	 * 发送手机验证码
	 * @param mobile
	 */
    public void sendsms(String mobile) {

    	//1.获取用户输入的手机号
		//2.在系统生成6位随机数字验证码(commons-lang RandomStringUtils)
		//randomNumeric:随机数字的字符串
		String code = RandomStringUtils.randomNumeric(6);

		//3.把验证码存入redis,时长5分钟
		redisTemplate.opsForValue().set("sendsms_" + mobile, code, 5, TimeUnit.MINUTES );

		//使用map集合来封装手机号和验证码
		Map map = new HashMap();
		map.put("mobile", mobile);
		map.put("code", code);

		//4.把手机号和验证码一起发送RabbitMQ
		rabbitMessagingTemplate.convertAndSend("codehub_sms", map);
	}

	/**
	 * 用户注册
	 * @param code
	 * @param user
	 * @return
	 */
    public Boolean register(String code, User user) {
    	//1.从redis取出验证码
		String redisCode = (String) redisTemplate.opsForValue().get("sendsms_" + user.getMobile());

		if (redisCode != null && code.equals(redisCode)){
			//初始用户信息
			user.setRegdate(new Date());
			user.setFanscount(0);
			user.setFollowcount(0);
			//保存用户信息
			add(user);
			//验证通过
			return true;
		}else{
			//验证不通过
			return false;
		}
	}

	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	public User login(User user) {
		//1.判断用户名是否存在
		User loginUser = userDao.findByMobile(user.getMobile());
		//2.如果用户名存在,判断密码是否一致
		if (loginUser != null && !encoder.matches(user.getPassword(), loginUser.getPassword())){
			//登录失败
			return null;
		}else {
			//登录成功
			return loginUser;
		}

	}

	/** 更新关注数 */
	@Transactional
    public void updateFollowcount(String userid, int x) {
		userDao.updateFollowcount(userid, x);
    }

	/** 更新粉丝数 */
	@Transactional
	public void updateFanscount(String userid, int x) {
		userDao.updateFanscount(userid, x);
	}
}
