package com.codehub.friend.dao;

import com.codehub.friend.pojo.NoFriend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/25
 *
 * 非好友Dao
 */
public interface NoFriendDao extends JpaRepository<NoFriend, String>{

}
