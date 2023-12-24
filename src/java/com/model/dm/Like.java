/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.dm;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Nguyen Huy Hoang
 */
public class Like {
    
    private String LikeId;
    private Date likeDate;
    private Time likeTime;
    private String PostId;
    private String UserId;

    public Like() {
    }

    public Like(String LikeId, Date likeDate, Time likeTime, String PostId, String UserId) {
        this.LikeId = LikeId;
        this.likeDate = likeDate;
        this.likeTime = likeTime;
        this.PostId = PostId;
        this.UserId = UserId;
    }

    public String getLikeId() {
        return LikeId;
    }

    public void setLikeId(String LikeId) {
        this.LikeId = LikeId;
    }

    public Date getLikeDate() {
        return likeDate;
    }

    public void setLikeDate(Date likeDate) {
        this.likeDate = likeDate;
    }

    public Time getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Time likeTime) {
        this.likeTime = likeTime;
    }

    public String getPostId() {
        return PostId;
    }

    public void setPostId(String PostId) {
        this.PostId = PostId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    @Override
    public String toString() {
        return "Like{" + "LikeId=" + LikeId + ", likeDate=" + likeDate + ", likeTime=" + likeTime + ", PostId=" + PostId + ", UserId=" + UserId + '}';
    }
    
}
