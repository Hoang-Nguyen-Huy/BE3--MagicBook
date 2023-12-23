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
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Nguyen Huy Hoang
 */
public class GroupAccessDAO implements I_DAO<GroupAccess> {

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
    public int delete(GroupAccess access) {

        int res = 0;
        try {

            Connection con = JDBCUtil.getConnection();

            String sql = "DELETE FROM groupaccess"
                    + " WHERE UserId = ? AND GroupId = ?";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, access.getUserId());
            pst.setString(2, access.getGroupId());

            res = pst.executeUpdate();

            JDBCUtil.closeConnection(con);

        } catch (Exception e) {

        }
        return res;

    }

    @Override
    public ArrayList<GroupAccess> selectAll() {

        ArrayList<GroupAccess> res = new ArrayList<>();
        try {

            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT * FROM groupaccess";

            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String accessId = rs.getString("AccessId");
                int isAdmin = rs.getInt("isAdmin");
                String userId = rs.getString("userId");
                String groupId = rs.getString("groupId");

                GroupAccess access = new GroupAccess(accessId, isAdmin, userId, groupId);

                res.add(access);
            }

            JDBCUtil.closeConnection(con);

        } catch (Exception e) {

        }
        return res;

    }

    @Override
    public GroupAccess selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<GroupAccess> selectByCondition(String condition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public ArrayList<GroupAccess> selectByUserGroup(String groupId) {

        ArrayList<GroupAccess> res = new ArrayList<>();
        try {

            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT * FROM groupaccess"
                    + " WHERE GroupId = ?";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, groupId);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String accessId = rs.getString("AccessId");
                int isAdmin = rs.getInt("isAdmin");
                String user = rs.getString("userId");
                String group = rs.getString("groupId");

                GroupAccess access = new GroupAccess(accessId, isAdmin, user, group);

                res.add(access);
            }
            JDBCUtil.closeConnection(con);
            
        } catch (Exception e) {

        }
        return res;

    }

    public int checkMember(String userId, String groupId) {

        ArrayList<GroupAccess> check = selectAll();
        for (GroupAccess access : check) {
            if (access.getUserId().equals(userId) && access.getGroupId().equals(groupId)) {
                return access.getIsAdmin();
            }
        }
        return -1;

    }

}
