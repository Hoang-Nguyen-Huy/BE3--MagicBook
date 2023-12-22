/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.model.dao.FriendshipDAO;
import com.model.dao.UserDAO;
import com.model.dm.Friendship;
import com.model.dm.User;
import com.utils.Util;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dell Latitude 7490
 */
public class FriendListController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String urlId = req.getParameter("id"); // id lay tu url
        Cookie[] cookies = req.getCookies();

        if (cookies == null) {  // neu ko co cookie thi khong duoc xem
            resp.sendRedirect(req.getContextPath());
            return;
        }

        User user = null;
        String userId = "";  // id cua nguoi dung dang login
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("id")) {
                userId = cookie.getValue();
                user = UserDAO.getInstance().checkAccessToHome(userId);
                break;
            }
        }
        if (user != null) {
            String accountName = user.getFirstName() + " " + user.getLastName();
            req.setAttribute("accountName", accountName);
            req.setAttribute("accountAvatar", user.getAvatar());
            req.setAttribute("accountId", userId);
        }

        if (urlId == null || urlId.equals(userId)) { // so sanh id tu url va id tu cookie
            if (user != null) {
                // khi account = true co nghia la profile cua nguoi dung dang log in
                req.setAttribute("account", true);

                String name = user.getFirstName() + " " + user.getLastName();
                req.setAttribute("userName", name);

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

                req.getRequestDispatcher("friendList.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath());
            }
        } else if (!urlId.equals(userId) || userId.equals("")) {
            // Khi account = false la profile cua nguoi khac, khong phai cua nguoi dang log in
            req.setAttribute("account", false);

            User other = UserDAO.getInstance().checkAccessToHome(urlId);

            String name = other.getFirstName() + " " + other.getLastName();
            req.setAttribute("userName", name);

            ArrayList<Friendship> friendshipList = FriendshipDAO.getInstance().selectByUserId(other.getUserId());
            ArrayList<User> friendList = new ArrayList<>();

            for (Friendship fs : friendshipList) {
                if (!fs.getReceiverId().equals(other.getUserId())) { // khi nguoi dung dang login khong phai la nguoi nhan ket ban
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

            req.getRequestDispatcher("friendList.jsp").forward(req, resp);
        }

    }

}
