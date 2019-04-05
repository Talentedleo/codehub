package com.codehub.user.dao;

import com.codehub.user.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * 数据访问接口
 * @author Weiping Li
 *
 */
public interface UserDao extends JpaRepository<User,String>,JpaSpecificationExecutor<User>{

    /** 根据手机号码查询 */
    public User findByMobile(String mobile);

    /** 更新关注数 */
    @Query("update User u set u.followcount = u.followcount + ?2 where  u.id = ?1")
    @Modifying
    public void updateFollowcount(String userid, int x);

    /** 更新粉丝数 */
    @Query("update User u set u.fanscount = u.fanscount + ?2 where  u.id = ?1")
    @Modifying
    public void updateFanscount(String userid, int x);
}
