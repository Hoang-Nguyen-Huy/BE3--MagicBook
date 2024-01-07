/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.dao;

import com.model.dm.Message;
import com.model.dm.User;
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
public class MessageDAO implements I_DAO<Message> {

    public static MessageDAO getInstance() {
        return new MessageDAO();
    }

    @Override
    public int insert(Message message) {

        int res = 0;
        try {

            Connection con = JDBCUtil.getConnection();

            String sql = "INSERT INTO message(MessageId, content, sentDate, sentTime, receiverId, UserId)"
                    + "VALUES (MD5(UUID()), ?, ?, ?, ?, ?)";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, Util.encrypt(message.getContent()));
            pst.setDate(2, message.getSentDate());
            pst.setTime(3, message.getSentTime());
            User receiver = UserDAO.getInstance().checkAccessToHome(message.getReceiverId());
            pst.setString(4, receiver.getUserId());
            User user = UserDAO.getInstance().checkAccessToHome(message.getUserId());
            pst.setString(5, user.getUserId());

            res = pst.executeUpdate();

            System.out.println("da luu tin nhan thanh cong");

            JDBCUtil.closeConnection(con);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;

    }

    @Override
    public int update(Message t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int delete(Message t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Message> selectAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Message selectById(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // select cac tin nhan cua nguoi gui
    public ArrayList<Message> selectUserId(String userId, String receiverId) {

        ArrayList<Message> res = new ArrayList<>();
        try {

            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT * FROM message"
                    + " WHERE (UserId = ? AND receiverId = ?)"
                    + " OR (receiverId = ? AND UserId = ?)"
                    + " ORDER BY sentDate, sentTime";

            PreparedStatement pst = con.prepareStatement(sql);

            User user = UserDAO.getInstance().checkAccessToHome(userId);
            User receiver = UserDAO.getInstance().checkAccessToHome(receiverId);            
            pst.setString(1, user.getUserId());
            pst.setString(2, receiver.getUserId());
            pst.setString(3, user.getUserId());
            pst.setString(4, receiver.getUserId());

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String messageId = rs.getString("MessageId");
                String content = Util.decrypt(rs.getString("content"));
                Date sentDate = rs.getDate("sentDate");
                Time sentTime = rs.getTime("sentTime");
                String ReceiverId = rs.getString("receiverId");
                String UserId = rs.getString("UserId");

                Message mess = new Message(messageId, content, sentDate, sentTime, ReceiverId, UserId);

                res.add(mess);
            }
            JDBCUtil.closeConnection(con);

        } catch (Exception e) {

        }
        return res;

    }

    // select cac tin nhan cua nguoi nhan 
    public ArrayList<Message> selectByReceiverId(String userId, String receiverId) {

        ArrayList<Message> res = new ArrayList<>();
        try {

            Connection con = JDBCUtil.getConnection();

            String sql = "SELECT * FROM message"
                    + " WHERE UserId = ? AND receiverId = ?"
                    + " ORDER BY sentDate, sentTime";

            PreparedStatement pst = con.prepareStatement(sql);

            pst.setString(1, receiverId);
            pst.setString(2, userId);

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                String messageId = rs.getString("MessageId");
                String content = rs.getString("content");
                Date sentDate = rs.getDate("sentDate");
                Time sentTime = rs.getTime("sentTime");
                String ReceiverId = rs.getString("receiverId");
                String UserId = rs.getString("UserId");

                Message mess = new Message(messageId, content, sentDate, sentTime, ReceiverId, UserId);

                res.add(mess);
            }
            JDBCUtil.closeConnection(con);

        } catch (Exception e) {

        }
        return res;

    }

    @Override
    public ArrayList<Message> selectByCondition(String condition) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
