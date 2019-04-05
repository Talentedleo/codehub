package com.codehub.friend.service;

import com.codehub.friend.client.UserClient;
import com.codehub.friend.dao.FriendDao;
import com.codehub.friend.dao.NoFriendDao;
import com.codehub.friend.pojo.Friend;
import com.codehub.friend.pojo.NoFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author Weiping Li
 * @Version 1.0
 * File Created on 2018/11/25
 *
 * 好友Service
 */
@Service
public class FriendService {

    @Autowired
    private FriendDao friendDao;

    @Autowired
    private NoFriendDao noFriendDao;

    @Autowired
    private UserClient userClient;


    /** 添加好友,做修改操作,必须要使用事务 */
    @Transactional
    public Integer addFriend(String userid, String friendid) {
        //判断有没有加过好友
        if (friendDao.selectCount(userid, friendid) > 0){
            //已经加过了
            return 0;
        }

        //没有加过好友
        //添加好友
        Friend friend = new Friend();
        friend.setUserid(userid);
        friend.setFriendid(friendid);
        friend.setIslike("0");
        friendDao.save(friend);

        //判断对方有没有加过你好友
        //如果加过的话,把双方的islike都改为1
        if (friendDao.selectCount(friendid, userid) > 0){
            friendDao.updateLike(userid, friendid, "1");
            friendDao.updateLike(friendid, userid, "1");
        }

        //远程调用用户微服务
        userClient.updateFollowcount(userid, 1);
        userClient.updateFanscount(friendid, 1);

        return 1;

    }

    /** 添加非好友 */
    public void addNoFriend(String userid, String friendid) {
        NoFriend noFriend = new NoFriend();
        noFriend.setUserid(userid);
        noFriend.setFriendid(friendid);

        noFriendDao.save(noFriend);
    }

    /** 删除好友 */
    @Transactional
    public void deleteFriend(String userid, String friendid) {
        //删除当前好友记录
        friendDao.deleteFriend(userid, friendid);

        //判断对方有没有加过当前用户
        if (friendDao.selectCount(friendid, userid) > 0){
            //把对方当前用户的记录的islike改为0
            friendDao.updateLike(friendid, userid, "0");
        }

        //远程调用用户微服务
        userClient.updateFollowcount(userid, -1);
        userClient.updateFanscount(friendid, -1);
    }
}
