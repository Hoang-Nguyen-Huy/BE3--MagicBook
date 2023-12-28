/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.model.dao.FriendshipDAO;
import com.model.dao.PostDAO;
import com.model.dao.UserDAO;
import com.model.dm.Friendship;
import com.model.dm.Post;
import com.model.dm.User;
import com.utils.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nguyen Huy Hoang
 */
public class ProfileController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = null;
        String userId = "";
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("id")) {
                userId = cookie.getValue();
                user = UserDAO.getInstance().checkAccessToHome(userId);
                break;
            }
        }

        String action = req.getParameter("action");
        System.out.println(action);
        // id lay tu url
        String IdReceiver = req.getParameter("id");
        System.out.println(IdReceiver);

        // de lay nguoi dung receiver tu db
        User receiver = UserDAO.getInstance().checkAccessToHome(IdReceiver);

        if ("addFriend".equals(action) && !IdReceiver.equals("") && user != null) {
            String status = "Pending";
            Friendship fs = new Friendship(status, user.getUserId(), receiver.getUserId());
            FriendshipDAO.getInstance().insert(fs);
        } else if ("cancelRequest".equals(action) && !IdReceiver.equals("") && user != null) {
            Friendship fs = new Friendship("Pending", user.getUserId(), receiver.getUserId());
            FriendshipDAO.getInstance().delete(fs);
        } else if ("beFriend".equals(action) && !IdReceiver.equals("") && user != null) {
            Friendship fs = new Friendship("Accepted", user.getUserId(), receiver.getUserId());
            FriendshipDAO.getInstance().update(fs);
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userId = req.getParameter("id"); //id tu url
        if (userId == null || userId.equals("")) {
            resp.sendRedirect("home");
            return;
        }
        User user = null;
        String id = "";  //id cua cookie
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
        if (user == null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }
        if (user != null) {
            String accountName = user.getFirstName() + " " + user.getLastName();
            req.setAttribute("accountName", accountName);
            req.setAttribute("accountAvatar", user.getAvatar());
            req.setAttribute("accountId", id);

        }

        if (userId == null || userId.equals(id)) {
            if (user != null) {
                // khi account = true co nghia la profile cua nguoi dung dang log in
                req.setAttribute("account", true);

                req.setAttribute("userId", id);
                String name = user.getFirstName() + " " + user.getLastName();
                req.setAttribute("userName", name);
                String country = user.getCountry();
                req.setAttribute("country", country);
                String sex = user.getSex();
                req.setAttribute("gender", sex);
                req.setAttribute("avatar", user.getAvatar());

                // hien thi cac bai post da dang
                ArrayList<Post> posts = PostDAO.getInstance().selectByUserId(user.getUserId());
                HashMap<User, Post> map = new HashMap<>();

                for (Post p : posts) {
                    map.put(UserDAO.getInstance().selectById(p.getUserId()), p);
                }

                Set<User> keyMap = map.keySet();

                for (User u : keyMap) {
                    u.setUserId(Util.encryptPassword(u.getUserId()));
                }

                for (Post p : posts) {
                    p.setPostId(Util.encryptPassword(p.getPostId()));
                }

                req.setAttribute("post", map);
                //------------------------------

                req.getRequestDispatcher("profile.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath());
            }
        } else if (!userId.equals(id) || id.equals("")) {
            // Khi account = false la profile cua nguoi khac, khong phai cua nguoi dang log in
            req.setAttribute("account", false);

            User receiver = UserDAO.getInstance().checkAccessToHome(userId); // lay thong nguoi dung tu id url
            // lan luot la id cua cookie, id tu url(receiver)
            Friendship fs = FriendshipDAO.getInstance().selectByUserReceiverId(user.getUserId(), receiver.getUserId());
            if (fs == null) {
                req.setAttribute("friendship", false); // 2 ben chua co ket ban --> hien nut Add friend cho ca ben GUI va NHAN
            } else if (fs.getStatus().equals("Accepted")) {
                req.setAttribute("friend", true); // 2 ben da thanh ban be --> hien nut Friend cho ca ben GUI va NHAN
                req.setAttribute("friendship", true);
            } else if (fs.getStatus().equals("Pending") && fs.getUserId().equals(user.getUserId())) { // nguoi GUI la nguoi dang login --> hien nut Cancel Request cho nguoi GUI
                req.setAttribute("friendship", true);
                req.setAttribute("friend", false);
                req.setAttribute("send", true);
            } else if (fs.getStatus().equals("Pending") && fs.getReceiverId().equals(user.getUserId())) { // nguoi NHAN la nguoi dang login --> hien 2 nut la Accept va Decline cho nguoi NHAN
                req.setAttribute("receive", true);
                req.setAttribute("friendship", true);
                req.setAttribute("friend", false);
            }

            ArrayList<User> list = UserDAO.getInstance().selectAll();
            for (User u : list) {
                if (Util.encryptPassword(u.getUserId()).equals(userId)) {
                    req.setAttribute("userId", userId);
                    String name = u.getFirstName() + " " + u.getLastName();
                    req.setAttribute("userName", name);
                    String country = u.getCountry();
                    req.setAttribute("country", country);
                    String sex = u.getSex();
                    req.setAttribute("gender", sex);
                    req.setAttribute("avatar", u.getAvatar());
                    req.getRequestDispatcher("profile.jsp").forward(req, resp);
                }
            }
        }

    }

}
