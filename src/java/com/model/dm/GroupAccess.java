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
public class GroupAccess {
    
    private String AccessId;
    private int isAdmin;
    private String userId;
    private String groupId;

    public GroupAccess() {
    }

    public GroupAccess(String AccessId, int isAdmin, String userId, String groupId) {
        this.AccessId = AccessId;
        this.isAdmin = isAdmin;
        this.userId = userId;
        this.groupId = groupId;
    }

    public GroupAccess(int isAdmin, String userId, String groupId) {
        this.isAdmin = isAdmin;
        this.userId = userId;
        this.groupId = groupId;
    }
    

    public String getAccessId() {
        return AccessId;
    }

    public void setAccessId(String AccessId) {
        this.AccessId = AccessId;
    }

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "GroupAccess{" + "AccessId=" + AccessId + ", isAdmin=" + isAdmin + ", userId=" + userId + ", groupId=" + groupId + '}';
    }
  
}
