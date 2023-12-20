/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.model.dao.UserDAO;
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
 * @author Nguyen Huy Hoang
 */
public class ProfileController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String userId = req.getParameter("id");
        User user = null;
        String id = "";
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("id")) {
                id = cookie.getValue();
                user = UserDAO.getInstance().checkAccessToHome(id);
                break;
            }
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
                req.getRequestDispatcher("profile.jsp").forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath());
            }
        } else if (!userId.equals(id) || id.equals("")) {
            // Khi account = false la profile cua nguoi khac, khong phai cua nguoi dang log in
            req.setAttribute("account", false);
            
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
