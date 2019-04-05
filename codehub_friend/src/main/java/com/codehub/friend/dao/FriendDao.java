package com.codehub.friend.dao;

import com.codehub.friend.pojo.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/25
 *
 * 好友Dao
 */
public interface FriendDao extends JpaRepository<Friend, String>{

    /** 查询好友记录 */
    @Query("select count(f) from Friend f where f.userid = ?1 and f.friendid = ?2")
    public Long selectCount(String userid, String friendid);

    /** 更新islike状态 */
    @Query("update Friend f set f.islike = ?3 where f.userid = ?1 and f.friendid = ?2")
    @Modifying
    public void updateLike(String userid, String friendid, String islike);

    /** 删除好友 */
    @Query("delete from Friend f where f.userid = ?1 and f.friendid = ?2")
    @Modifying
    public void deleteFriend(String userid, String friendid);
}
