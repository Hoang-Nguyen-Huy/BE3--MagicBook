/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.dao;

import com.model.dm.User;
import com.utils.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 *
 * @author Nguyen Huy Hoang
 */
public class UserDAO implements I_DAO<User>{
    
    public static UserDAO getInstance() {
        return new UserDAO();
    }

    @Override
    public int insert(User user) {
        
        int result = 0;
        
        try {
            
            Connection con = JDBCUtil.getConnection();
            
            String sql = "INSERT INTO user(firstName, lastName, dob, sex, country, phone, email, password)"
                    + "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, user.getFirstName());
            pst.setString(2, user.getLastName());
            pst.setDate(3, user.getDob());
            pst.setString(4, user.getSex());
            pst.setString(5, user.getCountry());
            pst.setString(6, user.getPhone());
            pst.setString(7, user.getEmail());
            pst.setString(8, user.getPassword());
            
            result = pst.executeUpdate();
            
            System.out.println("Sign up successful!!!");
            
            JDBCUtil.closeConnection(con);
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return result;
        
    }

    @Override
    public int update(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<User> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User selectById(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<User> selectByCondition(String condition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
