/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.dao;

import com.model.dm.Friendship;
import com.utils.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Nguyen Huy Hoang
 */
public class FriendshipDAO implements I_DAO<Friendship> {
    
    public static FriendshipDAO getInstance() {
        return new FriendshipDAO();
    }

    @Override
    public int insert(Friendship friendship) {
        
        int result = 0;
        try {
            Connection con = JDBCUtil.getConnection();
            
            String sql = "INSERT INTO Friendship(FriendshipId, status, receiverId, UserId)"
                    + "VALUES(MD5(UUID()), ?, ?, ?)";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, friendship.getStatus());
            pst.setString(2, friendship.getReceiverId());
            pst.setString(3, friendship.getUserId());
            
            result = pst.executeUpdate();
            
            JDBCUtil.closeConnection(con);
        } catch(SQLException e) {
            
        }
        return result;
        
    }

    @Override
    public int update(Friendship t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(Friendship friendship) {
        
        int result = 0;
        try {
            
            Connection con = JDBCUtil.getConnection();
            
            String sql = "DELETE FROM Friendship"
                    + " WHERE (UserId = ? AND receiverId = ?)"
                    + " OR (UserId = ? AND receiverId = ?)";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, friendship.getUserId());
            pst.setString(2, friendship.getReceiverId());
            pst.setString(3, friendship.getReceiverId());
            pst.setString(4, friendship.getUserId());  
            
            result = pst.executeUpdate();
            
            JDBCUtil.closeConnection(con);
            
        } catch(Exception e) {
            
        }
        return result;
        
    }

    @Override
    public ArrayList<Friendship> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Friendship selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Friendship> selectByCondition(String condition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
