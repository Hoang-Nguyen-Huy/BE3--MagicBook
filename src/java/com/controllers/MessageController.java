/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.model.dao.FriendshipDAO;
import com.model.dao.MessageDAO;
import com.model.dao.UserDAO;
import com.model.dm.Friendship;
import com.model.dm.Message;
import com.model.dm.User;
import com.utils.Util;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Nguyen Huy Hoang
 */
public class MessageController extends HttpServlet {

//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        User user = null;
//        String id = "";
//        Cookie[] cookies = req.getCookies();
//        if (cookies == null) {
//            resp.sendRedirect(req.getContextPath());
//            return;
//        }
//        for (Cookie cookie : cookies) {
//            if (cookie.getName().equals("id")) {
//                id = cookie.getValue();
//                user = UserDAO.getInstance().checkAccessToHome(id);
//                break;
//            }
//        }
//
//        String messageContent = req.getParameter("messageContent");
//        System.out.println(messageContent);
//
//        String receiverId = req.getParameter("id");
//
//        User receiver = UserDAO.getInstance().checkAccessToHome(receiverId);
//
//        Message mess = new Message();
//
//        Calendar calendar = Calendar.getInstance();
//        mess.setSentDate(new Date(calendar.getTime().getTime()));
//        mess.setSentTime(new Time(calendar.getTime().getTime()));
//
//        mess.setReceiverId(receiver.getUserId());
//        mess.setUserId(user.getUserId());
//
//        mess.setContent(messageContent);
//
//        if (messageContent == null || messageContent.equals("")) {
//            HttpSession session = req.getSession();
//            session.setAttribute("receiverId", receiverId);
//
//            resp.sendRedirect("message?id=" + receiverId);
//        } else {
//            MessageDAO.getInstance().insert(mess);
//            HttpSession session = req.getSession();
//            session.setAttribute("receiverId", receiverId);
//            
//            resp.sendRedirect("message?id=" + receiverId);
//        }
//
//    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = null;
        String id = "";
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("id")) {
                id = cookie.getValue();
                user = UserDAO.getInstance().checkAccessToHome(id);
                break;
            }
        }
        if (user != null) {           
            req.setAttribute("avatar", user.getAvatar());
            req.setAttribute("userId", id);
            String name = user.getFirstName() + ' ' + user.getLastName();
            req.setAttribute("userName", name);

            String receiverId = req.getParameter("id");
//            User receiver = UserDAO.getInstance().checkAccessToHome(receiverId);
//            req.setAttribute("receiverId", receiverId);
//            req.setAttribute("receiver", receiver);
//            String receiverName = receiver.getFirstName() + ' ' + receiver.getLastName();
//            System.out.println(receiverName);
//            req.setAttribute("receiverName", receiverName);

            ArrayList<Friendship> friendshipList = FriendshipDAO.getInstance().selectByUserId(user.getUserId());
            ArrayList<User> friendList = new ArrayList<>();

            for (Friendship fs : friendshipList) {
                if (!fs.getReceiverId().equals(user.getUserId())) { // khi nguoi dung dang login khong phai la nguoi nhan ket ban
                    User u = UserDAO.getInstance().selectById(fs.getReceiverId());
                    friendList.add(u);
                } else {
                    User u = UserDAO.getInstance().selectById(fs.getUserId());
                    friendList.add(u); // khi nguoi dung lang login la nguoi nhan ket ban
                }
            }

            // phai encrypt lai userid truoc khi duoc dua len url hoac la cookie
            for (User u : friendList) {
                u.setUserId(Util.encryptPassword(u.getUserId()));
            }

            req.setAttribute("friendList", friendList);
            
            HttpSession session = req.getSession();
            
            session.setAttribute("userId", id);
            session.setAttribute("userName", name);
            

            req.getRequestDispatcher("message.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath());

        }

    }

}
