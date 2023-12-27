/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.model.dao.CommentDAO;
import com.model.dao.PostDAO;
import com.model.dao.UserDAO;
import com.model.dm.Comment;
import com.model.dm.Post;
import com.model.dm.User;
import com.utils.Util;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
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
public class CommentController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = new User();
        String id = ""; // id lay tu cookie
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("id")) {
                id = cookie.getValue();
                user = UserDAO.getInstance().checkAccessToHome(id);
            }
        }

        String postId = req.getParameter("postid");
        String comment = req.getParameter("comment");
        System.out.println(comment);

        if (comment != null && !comment.equals("")) {
            Comment cmt = new Comment();
            cmt.setContent(comment);
            // lay post id
            Post cmtPost = PostDAO.getInstance().checkIdFromUrl(postId);
            cmt.setPostId(cmtPost.getPostId());

            // lay user id
            cmt.setUserId(user.getUserId());

            // de lay thoi gian thuc cua comment 
            Calendar calendar = Calendar.getInstance();
            cmt.setCommentDate(new Date(calendar.getTime().getTime()));
            cmt.setCommentTime(new Time(calendar.getTime().getTime()));
            System.out.println(cmt.getCommentDate());
            System.out.println(cmt.getCommentTime());

            CommentDAO.getInstance().insert(cmt);
            resp.sendRedirect("comment-post?postid=" + postId);
        } else {
            resp.sendRedirect("comment-post?postid=" + postId);
        }

    }

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
            req.setAttribute("userId", id);
            String name = user.getFirstName() + " " + user.getLastName();
            req.setAttribute("userName", name);
            req.setAttribute("avatar", user.getAvatar());

            String postId = req.getParameter("postid");
            Post cmtPost = PostDAO.getInstance().checkIdFromUrl(postId);
            User creator = UserDAO.getInstance().selectById(cmtPost.getUserId());

            //hien thi tat ca comment len browser
            ArrayList<Comment> cmts = CommentDAO.getInstance().selectByPostId(cmtPost.getPostId());
            HashMap<User, Comment> map = new HashMap<>();

            for (Comment cmt : cmts) {
                map.put(UserDAO.getInstance().selectById(cmt.getUserId()), cmt);
            }

            Set<User> keyMap = map.keySet();

            for (User u : keyMap) {
                u.setUserId(Util.encryptPassword(u.getUserId()));
            }

            for (Comment cmt : cmts) {
                cmt.setPostId(Util.encryptPassword(cmt.getPostId()));
            }

            for (Comment cmt : cmts) {
                System.out.println(cmt.toString());
            }
            
            System.out.println(cmts.size());
                
            req.setAttribute("cmt", map);
            //-----------------------------------
            
            creator.setUserId(Util.encryptPassword(creator.getUserId()));
            cmtPost.setPostId(Util.encryptPassword(cmtPost.getPostId()));

            req.setAttribute("creator", creator);
            req.setAttribute("cmtPost", cmtPost);

            req.getRequestDispatcher("commentPost.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath());
        }

    }

}
