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
public class Post {
    
    private String PostId;
    private String content;
    private String visibility;
    private Date postDate;
    private Time postTime;
    private String file;
    private String UserId;

    public Post() {
    }

    public Post(String PostId, String content, String visibility, Date postDate, Time postTime, String file, String UserId) {
        this.PostId = PostId;
        this.content = content;
        this.visibility = visibility;
        this.postDate = postDate;
        this.postTime = postTime;
        this.file = file;
        this.UserId = UserId;
    }

    public String getPostId() {
        return PostId;
    }

    public void setPostId(String PostId) {
        this.PostId = PostId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Time getPostTime() {
        return postTime;
    }

    public void setPostTime(Time postTime) {
        this.postTime = postTime;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    @Override
    public String toString() {
        return "Post{" + "PostId=" + PostId + ", content=" + content + ", visibility=" + visibility + ", postDate=" + postDate + ", postTime=" + postTime + ", file=" + file + ", UserId=" + UserId + '}';
    }
    
}
