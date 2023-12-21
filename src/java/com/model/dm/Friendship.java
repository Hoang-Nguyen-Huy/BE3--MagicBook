/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.dm;

/**
 *
 * @author Nguyen Huy Hoang
 */
public class Friendship {
    private String friendshipId;
    private String status;
    private String userId;
    private String receiverId;

    public Friendship() {
    }

    public Friendship(String friendshipId, String status, String userId, String receiverId) {
        this.friendshipId = friendshipId;
        this.status = status;
        this.userId = userId;
        this.receiverId = receiverId;
    }

    public Friendship(String status, String userId, String receiverId) {
        this.status = status;
        this.userId = userId;
        this.receiverId = receiverId;
    }
            
    public String getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(String friendshipId) {
        this.friendshipId = friendshipId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    @Override
    public String toString() {
        return "Friendship{" + "friendshipId=" + friendshipId + ", status=" + status + ", userId=" + userId + ", receiverId=" + receiverId + '}';
    }
    
    
}
