/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.dao;

import com.model.dm.GroupAccess;
import com.utils.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;

/**
 *
 * @author Nguyen Huy Hoang
 */
public class GroupAccessDAO implements I_DAO<GroupAccess>{

    public static GroupAccessDAO getInstance() {
        return new GroupAccessDAO();
    }
    
    @Override
    public int insert(GroupAccess access) {
        
        int res = 0;
        try {
            
            Connection con = JDBCUtil.getConnection();
            
            String sql = "INSERT INTO groupaccess(AccessId, isAdmin, UserId, GroupId)"
                    + "VALUES (MD5(UUID()), ?, ?, ?)";
            
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setInt(1, access.getIsAdmin());
            pst.setString(2, access.getUserId());
            pst.setString(3, access.getGroupId());
            
            res = pst.executeUpdate();
            
            JDBCUtil.closeConnection(con);
            
        } catch (Exception e) {
            
        }
        return res;
        
    }

    @Override
    public int update(GroupAccess t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(GroupAccess t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<GroupAccess> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GroupAccess selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<GroupAccess> selectByCondition(String condition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
