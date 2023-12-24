/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.dao;

import com.model.dm.Post;
import com.utils.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 *
 * @author Nguyen Huy Hoang
 */
public class PostDAO implements I_DAO<Post>{
    
    public static PostDAO getInstance() {
        return new PostDAO();
    }

    @Override
    public int insert(Post post) {
        
        int res = 0;
        try {
            
            Connection con = JDBCUtil.getConnection();
            
            String sql = "INSERT INTO post(PostId, content, visibility, postDate, postTime, file, UserId)"
                    + "VALUES(MD5(UUID()), ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, post.getContent());
            pst.setString(2, post.getVisibility());
            pst.setDate(3, post.getPostDate());
            pst.setTime(4, post.getPostTime());
            pst.setString(5, post.getFile());
            pst.setString(6, post.getUserId());
            
            res = pst.executeUpdate();
            
            System.out.println("insert thanh cong");
            
            JDBCUtil.closeConnection(con);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        return res;
        
    }

    @Override
    public int update(Post t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(Post t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Post> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Post selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Post> selectByCondition(String condition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
