/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.model.dao.GroupAccessDAO;
import com.model.dao.GroupDAO;
import com.model.dao.UserDAO;
import com.model.dm.Group;
import com.model.dm.GroupAccess;
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
public class MemberListController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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
        } else {
            resp.sendRedirect(req.getContextPath());
            return;
        }

        String urlGroupId = req.getParameter("groupid");
        if (urlGroupId == null) {
            resp.sendRedirect("home");
            return;
        }
        Group gr = GroupDAO.getInstance().checkIdFromUrl(urlGroupId);

        ArrayList<GroupAccess> list = GroupAccessDAO.getInstance().selectByUserGroup(gr.getGroupId());

        ArrayList<User> member = new ArrayList<>();

        for (GroupAccess a : list) {
            member.add(UserDAO.getInstance().selectById(a.getUserId()));
        }
        
        for (User u : member) {
            u.setUserId(Util.encryptPassword(u.getUserId()));
        }
        
        req.setAttribute("memberlist", member);

        req.getRequestDispatcher("memberList.jsp").forward(req, resp);

    }

}
