/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.dao;

import com.model.dm.Comment;
import com.utils.JDBCUtil;
import com.utils.Util;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;

/**
 *
 * @author Nguyen Huy Hoang
 */
public class CommentDAO implements I_DAO<Comment>{

    public static CommentDAO getInstance() {
        return new CommentDAO();
    }
        
    @Override
    public int insert(Comment cmt) {
        
        int res = 0;
        try {
            
            Connection con = JDBCUtil.getConnection();
            
            String sql = "INSERT INTO comment(CommentId, content, commentDATE, commentTIME, PostId, UserId)"
                    + "VALUES(MD5(UUID()), ?, ?, ?, ?, ?)";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, cmt.getContent());
            pst.setDate(2, cmt.getCommentDate());
            pst.setTime(3, cmt.getCommentTime());
            pst.setString(4, cmt.getPostId());
            pst.setString(5, cmt.getUserId());
            
            res = pst.executeUpdate();
            
            JDBCUtil.closeConnection(con);
            
        } catch(Exception e) {
            
        }
        return res;
        
    }

    @Override
    public int update(Comment t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(Comment t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int deleteByPostId(String postId) {
        
        int res = 0;
        try {
            
            Connection con = JDBCUtil.getConnection();
            
            String sql = "DELETE FROM comment WHERE PostId = ?";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, postId);
            
            res = pst.executeUpdate();
            
            JDBCUtil.closeConnection(con);
            
        } catch(Exception e) {
            
        }  
        return res;
        
    }

    @Override
    public ArrayList<Comment> selectAll() {
        
        ArrayList<Comment> res = new ArrayList<>();
        try {
            
            Connection con = JDBCUtil.getConnection();
            
            String sql = "SELECT * FROM comment";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                String id = rs.getString("CommentId");
                String content = rs.getString("content");
                Date commentDate = rs.getDate("commentDATE");
                Time commentTime = rs.getTime("commentTIME");
                String postId = rs.getString("PostId");
                String userId = rs.getString("UserId");
                
                Comment cmt = new Comment(id, content, commentDate, commentTime, postId, userId);
                
                res.add(cmt);
            }
            
            JDBCUtil.closeConnection(con);
            
        } catch(Exception e) {
            
        }
        return res;
        
    }

    @Override
    public Comment selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public ArrayList<Comment> selectByPostId(String postId) {
        
        ArrayList<Comment> res = new ArrayList<>();
        try {
            
            Connection con = JDBCUtil.getConnection();
            
            String sql = "SELECT * FROM comment WHERE PostId = ?";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, postId);
            
            ResultSet rs = pst.executeQuery();
            
            while(rs.next()) {
                String id = rs.getString("CommentId");
                String content = rs.getString("content");
                Date commentDate = rs.getDate("commentDATE");
                Time commentTime = rs.getTime("commentTIME");
                String postid = rs.getString("PostId");
                String userId = rs.getString("UserId");
                
                Comment cmt = new Comment(id, content, commentDate, commentTime, postid, userId);
                
                res.add(cmt);
            }
            
            JDBCUtil.closeConnection(con);
            
        } catch(Exception e) {
            
        }
        return res;
        
    }

    @Override
    public ArrayList<Comment> selectByCondition(String condition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int countComment(String postId) {
        
        ArrayList<Comment> check = selectAll();
        int count = 0;
        for (Comment cmt : check) {
            if (postId.equals(Util.encryptPassword(cmt.getPostId()))) {
                count++;
            }
        }
        return count;
        
    }
    
}
