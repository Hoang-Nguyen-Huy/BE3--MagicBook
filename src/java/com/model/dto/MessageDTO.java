/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.sql.Date;
import java.sql.Time;
/**
 *
 * @author Nguyen Huy Hoang
 */
public class MessageDTO {
    
    @JsonProperty("messageId")
    private String MessageId;
    @JsonProperty("content")
    private String content;
    @JsonProperty("sentDate")
    private Date sentDate;
    @JsonProperty("sentTime")
    private Time sentTime;
    @JsonProperty("receiverId")
    private String receiverId;
    @JsonProperty("userId")
    private String UserId;

    public MessageDTO() {
    }

    @JsonCreator
    public MessageDTO(@JsonProperty("messageId") String MessageId, @JsonProperty("content") String content,
             @JsonProperty("sentDate") Date sentDate, @JsonProperty("sentTime") Time sentTime,
              @JsonProperty("receiverId") String receiverId, @JsonProperty("userId") String UserId) {
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
        return "MessageDTO{" + "MessageId=" + MessageId + ", content=" + content + ", sentDate=" + sentDate + ", sentTime=" + sentTime + ", receiverId=" + receiverId + ", UserId=" + UserId + '}';
    }
     
}
