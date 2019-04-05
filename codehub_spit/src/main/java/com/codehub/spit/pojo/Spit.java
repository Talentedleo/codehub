package com.codehub.spit.pojo;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 吐槽
 */
@Getter
@Setter
@Document(collection = "spit")
public class Spit implements Serializable{

    @Id  //映射集合的_id字段
    private String id;
    private String content;
    private Date publishtime;
    private String userid;
    private String nickname;
    private Integer visits;
    private Integer thumbup;
    private Integer share;
    private Integer comment;
    private String state;
    private String parentid;//父id

    public Spit(String id, String content, Date publishtime, String userid, String nickname, Integer visits, Integer thumbup, Integer share, Integer comment, String state, String parentid) {
        this.id = id;
        this.content = content;
        this.publishtime = publishtime;
        this.userid = userid;
        this.nickname = nickname;
        this.visits = visits;
        this.thumbup = thumbup;
        this.share = share;
        this.comment = comment;
        this.state = state;
        this.parentid = parentid;
    }

    public Spit() {
    }

}
