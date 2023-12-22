/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.dao;

import com.model.dm.User;
import com.utils.JDBCUtil;
import com.utils.Util;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Nguyen Huy Hoang
 */
public class UserDAO implements I_DAO<User> {

    public static UserDAO getInstance() {
        return new UserDAO();
    }

    @Override
    public int insert(User user) {

        if (checkDuplicateEmail(user.getEmail()) == false || checkDuplicatePhone(user.getPhone()) == false) {
            return 0;
        }

        int result = 0;

        try {

            Connection con = JDBCUtil.getConnection();

            String sql = "INSERT INTO user(UserId, firstName, lastName, dob, sex, country, phone, email, password)"
                    + "VALUES(MD5(UUID()), ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, user.getFirstName());
            pst.setString(2, user.getLastName());
            pst.setDate(3, user.getDob());
            pst.setString(4, user.getSex());
            pst.setString(5, user.getCountry());
            pst.setString(6, user.getPhone());
            pst.setString(7, user.getEmail());
            pst.setString(8, Util.encryptPassword(user.getPassword())); // mã hóa mật khẩu trước khi đưa vào db

            result = pst.executeUpdate();

            System.out.println("Sign up successful!!!");

            JDBCUtil.closeConnection(con);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }

    @Override
    public int update(User user) {
        return 0;
    }

    public int updateProfile(User user, boolean upPassword) {

        int result = 0;
        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "UPDATE user"
                    + " SET "
                    + "firstName = ?"
                    + ", lastName = ?"
                    + ", dob = ?"
                    + ", sex = ?"
                    + ", country = ?"
                    + ", phone = ?"
                    + ", email = ?"
                    + ", password = ?"
                    + ", avatar = ?"
                    + " WHERE UserId = ?";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, user.getFirstName());
            pst.setString(2, user.getLastName());
            pst.setDate(3, user.getDob());
            pst.setString(4, user.getSex());
            pst.setString(5, user.getCountry());
            pst.setString(6, user.getPhone());
            pst.setString(7, user.getEmail());
            if (upPassword == true) {
                pst.setString(8, Util.encryptPassword(user.getPassword()));
            } else {
                pst.setString(8, user.getPassword());
            }
            pst.setString(9, user.getAvatar());
            pst.setString(10, user.getUserId());

            result = pst.executeUpdate();

            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    @Override
    public int delete(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<User> selectAll() {

        ArrayList<User> res = new ArrayList<User>();
        try {

            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT * FROM user";

            PreparedStatement pst = con.prepareStatement(sql);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String id = rs.getString("UserId");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                Date dob = rs.getDate("dob");
                String sex = rs.getString("sex");
                String country = rs.getString("country");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String avatar = rs.getString("avatar");

                User user = new User(id, firstName, lastName, dob, sex, country, phone, email, password, avatar);

                res.add(user);
            }
            JDBCUtil.closeConnection(con);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;

    }

    @Override
    public User selectById(String id) {

        User result = null;
        try {

            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT * FROM user"
                    + " WHERE UserId = ?";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, id);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String userId = rs.getString("UserId");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                Date dob = rs.getDate("dob");
                String sex = rs.getString("sex");
                String country = rs.getString("country");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String avatar = rs.getString("avatar");

                result = new User(userId, firstName, lastName, dob, sex, country, phone, email, password, avatar);
            }
            JDBCUtil.closeConnection(con);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    @Override
    public ArrayList<User> selectByCondition(String condition) {

        ArrayList<User> res = new ArrayList<>();
        try {
            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT * FROM user"
                    + " WHERE (firstName LIKE ? OR lastName LIKE ?)"
                    + " OR CONCAT(firstName, ' ', lastName) LIKE ?";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, "%" + condition.trim() + "%");
            pst.setString(2, "%" + condition.trim() + "%");
            pst.setString(3, condition.trim());

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String userId = rs.getString("UserId");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                Date dob = rs.getDate("dob");
                String sex = rs.getString("sex");
                String country = rs.getString("country");
                String phone = rs.getString("phone");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String avatar = rs.getString("avatar");

                User user = new User(userId, firstName, lastName, dob, sex, country, phone, email, password, avatar);

                res.add(user);
            }

            JDBCUtil.closeConnection(con);
        } catch (Exception e) {

        }
        return res;

    }

    public String login(String email, String password) {

        String result = "";
        try {

            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT * FROM user WHERE email = ? AND password = ?";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, email);
            pst.setString(2, Util.encryptPassword(password)); //mã hóa mật khẩu để so sánh với mật khẩu có trong db

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                if (rs.getString("email").equals(email) && rs.getString("password").equals(Util.encryptPassword(password))) {
                    result = rs.getString("UserId");
                }
            }

        } catch (SQLException e) {
            result = "";
        }
        return result;

    }

    public User checkAccessToHome(String id) {

        User res = null;
        ArrayList<User> check = selectAll();
        for (User user : check) {
            if (Util.encryptPassword(user.getUserId()).equals(id)) {
                res = user;
            }
        }
        return res;
    }

    public boolean checkDuplicateEmail(String email) {

        ArrayList<User> check = selectAll();
        for (User user : check) {
            if (email.trim().equals(user.getEmail())) {
                return false;
            }
        }
        return true;

    }

    public boolean checkDuplicatePhone(String phone) {

        ArrayList<User> check = selectAll();
        for (User user : check) {
            if (phone.trim().equals(user.getPhone())) {
                return false;
            }
        }
        return true;

    }

}
