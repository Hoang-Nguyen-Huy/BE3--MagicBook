/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.dao;

import com.model.dm.Friendship;
import com.model.dm.User;
import com.utils.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
    public int update(Friendship friendship) {
        
        int res = 0;
        try {
            
            Connection con = JDBCUtil.getConnection();
            
            String sql = "UPDATE friendship"
                    + " SET "
                    + "status = ?"
                    + " WHERE (UserId = ? AND receiverId = ?)"
                    + " OR (UserId = ? AND receiverId = ?)";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, friendship.getStatus());
            pst.setString(2, friendship.getUserId());
            pst.setString(3, friendship.getReceiverId());
            pst.setString(4, friendship.getReceiverId());
            pst.setString(5, friendship.getUserId()); 
            
            res = pst.executeUpdate();
            
            JDBCUtil.closeConnection(con);
            
        } catch(Exception e) {
            
        }
        return res;
        
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
    
    public Friendship selectByUserReceiverId(String userId, String receiverId) {
        
        Friendship res = null;
        try {
            
            Connection con = JDBCUtil.getConnection();
            
            String sql = "SELECT * FROM Friendship"
                    + " WHERE (UserId = ? AND receiverId = ?)"
                    + " OR (UserId = ? AND receiverId = ?)";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, userId);
            pst.setString(2, receiverId);
            pst.setString(3, receiverId);
            pst.setString(4, userId);  
            
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                String friendshipId = rs.getString("FriendshipId");
                String status = rs.getString("status");
                String receiver = rs.getString("receiverId");
                String user = rs.getString("UserId");
                
                res = new Friendship(friendshipId, status, user, receiver);
            }
            JDBCUtil.closeConnection(con);
            
        } catch (Exception e) {
            
        }
        return res;
        
    }
    
    public Friendship isFriend(String userId, String receiverId) {
        
        Friendship res = null;
        try {
            
            Connection con = JDBCUtil.getConnection();
            
            String sql = "SELECT * FROM Friendship"
                    + " WHERE (UserId = ? AND receiverId = ?)"
                    + " OR (UserId = ? AND receiverId = ?)";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            User user = UserDAO.getInstance().checkAccessToHome(userId);
            User receiver = UserDAO.getInstance().checkAccessToHome(receiverId);
            
            pst.setString(1, user.getUserId());
            pst.setString(2, receiver.getUserId());
            pst.setString(3, receiver.getUserId());
            pst.setString(4, user.getUserId());  
            
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                String friendshipId = rs.getString("FriendshipId");
                String status = rs.getString("status");
                String receiverID = rs.getString("receiverId");
                String userID = rs.getString("UserId");
                
                res = new Friendship(friendshipId, status, userID, receiverID);
            }
            JDBCUtil.closeConnection(con);
            
        } catch (Exception e) {
            
        }
        return res;
        
    }
    
    public ArrayList<Friendship> selectByUserId (String userId) {
        
        ArrayList<Friendship> res = new ArrayList<>();
        try {
            
            Connection con = JDBCUtil.getConnection();
            
            String sql = "SELECT * FROM friendship"
                    + " WHERE (UserId = ? OR receiverId = ?)"
                    + " AND status = 'Accepted'";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, userId);
            pst.setString(2, userId);
            
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                String friendshipId = rs.getString("FriendshipId");
                String status = rs.getString("status");
                String receiver = rs.getString("receiverId");
                String user = rs.getString("UserId");
                
                Friendship fs = new Friendship(friendshipId, status, receiver, user);
                res.add(fs);
            }
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            
        }
        return res;
    }

    @Override
    public ArrayList<Friendship> selectByCondition(String condition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
