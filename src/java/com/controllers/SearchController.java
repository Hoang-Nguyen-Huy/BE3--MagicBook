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
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dell Latitude 7490
 */
public class SearchController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String searchName = req.getParameter("u");
        ArrayList<User> found = UserDAO.getInstance().selectByCondition(searchName);
        User user = null;
        String id = "";
        Cookie[] cookies = req.getCookies();
        if(cookies == null) {
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
        // neu id bi trung vs id cua cookie thi se xoa ra khoi list
        Iterator<User> iterator = found.iterator();
        while (iterator.hasNext()) {
            User u = iterator.next();
            if (id.equals(Util.encryptPassword(u.getUserId()))) {
                iterator.remove();
            }
        }
        for (User u : found) {
            u.setUserId(Util.encryptPassword(u.getUserId()));
        }
        if (user != null) {
            req.setAttribute("userId", id);
            String name = user.getFirstName() + " " + user.getLastName();
            req.setAttribute("userName", name);
            req.setAttribute("avatar", user.getAvatar());
            req.setAttribute("found", found);
            req.getRequestDispatcher("searchPeople.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath());
        }

    }

}
