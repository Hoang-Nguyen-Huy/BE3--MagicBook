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
public class Message {
    
    private String MessageId;
    private String content;
    private Date sentDate;
    private Time sentTime;
    private String receiverId;
    private String UserId;

    public Message() {
    }

    public Message(String MessageId, String content, Date sentDate, Time sentTime, String receiverId, String UserId) {
        this.MessageId = MessageId;
        this.content = content;
        this.sentDate = sentDate;
        this.sentTime = sentTime;
        this.receiverId = receiverId;
        this.UserId = UserId;
    }

    public String getMessageId() {
        return MessageId;
    }

    public void setMessageId(String MessageId) {
        this.MessageId = MessageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public Time getSentTime() {
        return sentTime;
    }

    public void setSentTime(Time sentTime) {
        this.sentTime = sentTime;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String UserId) {
        this.UserId = UserId;
    }

    @Override
    public String toString() {
        return "Message{" + "MessageId=" + MessageId + ", content=" + content + ", sentDate=" + sentDate + ", sentTime=" + sentTime + ", receiverId=" + receiverId + ", UserId=" + UserId + '}';
    }
    
}
