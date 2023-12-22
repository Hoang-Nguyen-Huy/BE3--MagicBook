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
public class Group {
    private String GroupId;
    private String name;
    private String avatar;
    private String userId;

    public Group() {
    }

    public Group(String GroupId, String name, String avatar, String userId) {
        this.GroupId = GroupId;
        this.name = name;
        this.avatar = avatar;
        this.userId = userId;
    }

    public Group(String name, String avatar, String userId) {
        this.name = name;
        this.avatar = avatar;
        this.userId = userId;
    }
    

    public String getGroupId() {
        return GroupId;
    }

    public void setGroupId(String GroupId) {
        this.GroupId = GroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Group{" + "GroupId=" + GroupId + ", name=" + name + ", avatar=" + avatar + ", userId=" + userId + '}';
    }
    
    
}
