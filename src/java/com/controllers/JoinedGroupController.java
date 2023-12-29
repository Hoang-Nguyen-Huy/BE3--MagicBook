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
public class JoinedGroupController extends HttpServlet {

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
        } else {
            resp.sendRedirect(req.getContextPath());
            return;
        }

        if (urlId == null || urlId.equals(userId)) {
            if (user != null) {
                // khi account = true co nghia la profile cua nguoi dung dang log in
                req.setAttribute("account", true);

                String name = user.getFirstName() + " " + user.getLastName();
                req.setAttribute("userName", name);

                // hien thi cac group ma nguoi dung da join
                ArrayList<GroupAccess> access = GroupAccessDAO.getInstance().selectJoinedGroup(user.getUserId());
                ArrayList<Group> joinedGroup = new ArrayList<>();

                for (GroupAccess a : access) {
                    joinedGroup.add(GroupDAO.getInstance().selectById(a.getGroupId()));
                }

                for (Group gr : joinedGroup) {
                    gr.setGroupId(Util.encryptPassword(gr.getGroupId()));
                    gr.setUserId(Util.encryptPassword(gr.getUserId()));
                }

                req.setAttribute("joinGroupList", joinedGroup);
                //-----------------------------------------

                req.getRequestDispatcher("joinedGroup.jsp").forward(req, resp);
            }
        } else if (!urlId.equals(userId) || userId.equals("")) {
            // Khi account = false la profile cua nguoi khac, khong phai cua nguoi dang log in
            req.setAttribute("account", false);

            User other = UserDAO.getInstance().checkAccessToHome(urlId);

            String name = other.getFirstName() + " " + other.getLastName();
            req.setAttribute("userName", name);

            // hien thi cac group ma nguoi dung da join
            ArrayList<GroupAccess> access = GroupAccessDAO.getInstance().selectJoinedGroup(other.getUserId());
            ArrayList<Group> joinedGroup = new ArrayList<>();

            for (GroupAccess a : access) {
                joinedGroup.add(GroupDAO.getInstance().selectById(a.getGroupId()));
            }

            for (Group gr : joinedGroup) {
                gr.setGroupId(Util.encryptPassword(gr.getGroupId()));
                gr.setUserId(Util.encryptPassword(gr.getUserId()));
            }

            req.setAttribute("joinGroupList", joinedGroup);
            //-----------------------------------------

            req.getRequestDispatcher("joinedGroup.jsp").forward(req, resp);
        }

    }

}
