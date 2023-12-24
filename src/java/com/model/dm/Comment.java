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
public class Comment {
    
    private String CommentId;
    private String content;
    private Date commentDate;
    private Time commentTime;
    private String PostId;
    private String UserId;

    public Comment() {
    }

    public Comment(String CommentId, String content, Date commentDate, Time commentTime, String PostId, String UserId) {
        this.CommentId = CommentId;
        this.content = content;
        this.commentDate = commentDate;
        this.commentTime = commentTime;
        this.PostId = PostId;
        this.UserId = UserId;
    }

    public String getCommentId() {
        return CommentId;
    }

    public void setCommentId(String CommentId) {
        this.CommentId = CommentId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public Time getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Time commentTime) {
        this.commentTime = commentTime;
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
        return "Comment{" + "CommentId=" + CommentId + ", content=" + content + ", commentDate=" + commentDate + ", commentTime=" + commentTime + ", PostId=" + PostId + ", UserId=" + UserId + '}';
    }
    
}
