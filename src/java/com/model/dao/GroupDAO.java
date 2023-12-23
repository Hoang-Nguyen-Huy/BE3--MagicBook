/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.dao;

import com.model.dm.Group;
import com.utils.JDBCUtil;
import com.utils.Util;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Nguyen Huy Hoang
 */
public class GroupDAO implements I_DAO<Group>{
    
    public static GroupDAO getInstance() {
        return new GroupDAO();
    }
    
    @Override
    public int insert(Group group) {
        
        int res = 0;
        try {
            
            Connection con = JDBCUtil.getConnection();
            
            String sql = "INSERT INTO gr(GroupId, name, avatar, UserId)"
                    + "VALUES (MD5(UUID()), ?, ?, ?)";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, group.getName());
            pst.setString(2, group.getAvatar());
            pst.setString(3, group.getUserId());
            
            res = pst.executeUpdate();
            
            JDBCUtil.closeConnection(con);
            
        } catch (Exception e) {
            
        }
        return res;
        
    }

    @Override
    public int update(Group t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(Group t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Group> selectAll() {
        
        ArrayList<Group> res = new ArrayList<>();
        try {
            
            Connection con = JDBCUtil.getConnection();
            
            String sql = "SELECT * FROM gr";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                String groupId = rs.getString("GroupId");
                String name = rs.getString("name");
                String avatar = rs.getString("avatar");
                String userId = rs.getString("UserId");
                
                Group gr = new Group(groupId, name, avatar, userId);
                
                res.add(gr);
            }
            JDBCUtil.closeConnection(con);
            
        } catch(Exception e) {
            
        }
        return res;
        
    }

    @Override
    public Group selectById(String id) {
        
        Group res = null;
        try {
            
            Connection con = JDBCUtil.getConnection();
            
            String sql = "SELECT * FROM gr"
                    + " WHERE GroupId = ?";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, id);
            
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                String groupId = rs.getString("GroupId");
                String name = rs.getString("name");
                String avatar = rs.getString("avatar");
                String userId = rs.getString("UserId");
                
                res = new Group(groupId, name, avatar, userId);
            }
            JDBCUtil.closeConnection(con);
            
        } catch(Exception e) {
            
        }
        return res;
        
    }
    
    public ArrayList<Group> selectByUserId (String userId) {
        
        ArrayList<Group> res = new ArrayList<>();
        try {
            
            Connection con = JDBCUtil.getConnection();
            
            String sql = "SELECT * FROM gr"
                    + " WHERE UserId = ?";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, userId);
            
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                String groupId = rs.getString("GroupId");
                String name = rs.getString("name");
                String avatar = rs.getString("avatar");
                String id = rs.getString("UserId");
                
                Group gr = new Group(groupId, name, avatar, id);
                
                res.add(gr);
            }
            JDBCUtil.closeConnection(con);
        } catch(Exception e) {
            
        }
        return res;
        
    }

    @Override
    public ArrayList<Group> selectByCondition(String condition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Group checkIdFromUrl(String id) { // ham nay tuong tu ham checkAccessToHome ben UserDAO
        
        ArrayList<Group> check = selectAll();
        Group gr = null;
        for (Group g : check) {
            if (Util.encryptPassword(g.getGroupId()).equals(id)) {
                gr = g;
            }
        }
        return gr;
        
    }
    
    public boolean checkCreator(String userId, String groupId) {
        
        ArrayList<Group> check = selectAll();
        for (Group gr : check) {
            if (gr.getUserId().equals(userId) && gr.getGroupId().equals(groupId)) {
                return true;
            }
        }
        return false;
        
    }
    
}
