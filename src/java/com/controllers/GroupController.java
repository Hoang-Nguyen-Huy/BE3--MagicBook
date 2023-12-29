/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.model.dao.CommentDAO;
import com.model.dao.GroupAccessDAO;
import com.model.dao.GroupDAO;
import com.model.dao.LikeDAO;
import com.model.dao.PostDAO;
import com.model.dao.UserDAO;
import com.model.dm.Group;
import com.model.dm.GroupAccess;
import com.model.dm.Post;
import com.model.dm.User;
import com.utils.Util;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Nguyen Huy Hoang
 */
public class GroupController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = new User();
        String userId = "";   // id lay tu cookie
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            resp.sendRedirect(req.getContextPath());
            return;
        }
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("id")) {
                userId = cookie.getValue();
                user = UserDAO.getInstance().checkAccessToHome(userId);
                break;
            }
        }

        String postFile = "";
        Post post = new Post();

        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();

            ServletContext servletContext = this.getServletConfig().getServletContext();
            File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
            factory.setRepository(repository);

            ServletFileUpload upload = new ServletFileUpload(factory);

            List<FileItem> items = upload.parseRequest(req);

            Iterator<FileItem> iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = iter.next();

                if (item.isFormField()) {
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    System.out.println("name: " + name + "value: " + value);

                    if ("postContent".equals(name)) {
                        if (!value.isEmpty()) {
                            post.setContent(value.trim());
                            System.out.println(value);
                            // de lay thoi gian thuc cua bai dang 
                            Calendar calendar = Calendar.getInstance();
                            post.setPostDate(new Date(calendar.getTime().getTime()));
                            post.setPostTime(new Time(calendar.getTime().getTime()));
                            System.out.println(post.getPostDate());
                            System.out.println(post.getPostTime());
                            // lay userId cua nguoi dang
                            post.setUserId(user.getUserId());
                            System.out.println(post.getUserId());
                        }
                    }
                } else {
                    if ("fileInput".equals(item.getFieldName())) {
                        postFile = item.getName();
                        if (postFile.equals("")) {
                            break;
                        } else {
                            Path path = Paths.get(postFile);
                            String storePath = servletContext.getRealPath("/postContent");
                            File uploadFile = new File(storePath + "/" + path.getFileName());
                            item.write(uploadFile);
                            System.out.println(storePath + "/" + path.getFileName());
                            post.setFile("postContent\\" + path.getFileName());
                            // de lay thoi gian thuc cua bai dang 
                            Calendar calendar = Calendar.getInstance();
                            post.setPostDate(new Date(calendar.getTime().getTime()));
                            post.setPostTime(new Time(calendar.getTime().getTime()));
                            System.out.println(post.getPostDate());
                            System.out.println(post.getPostTime());
                        }
                    }
                }
            }
        } catch (Exception e) {

        }

        String action = req.getParameter("action");
        System.out.println(action);
        String groupId = req.getParameter("groupid"); // lay groupId tu url
        System.out.println(groupId);
        Group gr = GroupDAO.getInstance().checkIdFromUrl(groupId);

        if ("joinGroup".equals(action) && !groupId.equals("")) {
            GroupAccess access = new GroupAccess(0, user.getUserId(), gr.getGroupId());
            GroupAccessDAO.getInstance().insert(access);
        } else if ("leaveGroup".equals(action) && !groupId.equals("")) {
            GroupAccess access = new GroupAccess(0, user.getUserId(), gr.getGroupId());
            GroupAccessDAO.getInstance().delete(access);
        }

        //delete post --------------------
        String actionPost = req.getParameter("actionPost");
        String delPostId = req.getParameter("postId");
        Post delPost = PostDAO.getInstance().checkIdFromUrl(delPostId);

        if ("delete".equals(actionPost) && delPost != null) {
            System.out.println(delPost.toString());
            LikeDAO.getInstance().deleteByPostId(delPost.getPostId());
            CommentDAO.getInstance().deleteByPostId(delPost.getPostId());
            PostDAO.getInstance().delete(delPost);
            System.out.println(actionPost);
        } else if ("cancelDelete".equals(action) && delPost != null) {
            System.out.println(action);
            System.out.println(delPost.toString());
        }
        //------------------------

        // up Post -----------------
        if (post.getContent() != null || post.getFile() != null) {
            post.setVisibility(gr.getGroupId());
            System.out.println(post.toString());
            PostDAO.getInstance().insert(post);
            resp.sendRedirect("group?groupid=" + groupId);
        } else {
            resp.sendRedirect("group?groupid=" + groupId);
        }
        //-----------------------

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = new User();
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

        String groupId = req.getParameter("groupid"); // lay groupId tu url
        if (groupId == null) {
            resp.sendRedirect("home");
            return;
        }
        req.setAttribute("groupId", groupId);

        Group gr = GroupDAO.getInstance().checkIdFromUrl(groupId);

        req.setAttribute("name", gr.getName());
        req.setAttribute("avatar", gr.getAvatar());

        // kiem tra xem nguoi dung dang log co phai la nguoi tao gr hay khong
        if (GroupDAO.getInstance().checkCreator(user.getUserId(), gr.getGroupId())) { // account = true thi hien nut Update, Delete, Post, Grant Admin
            // account = true khi nguoi tao gr la nguoi dang log
            req.setAttribute("account", true);

            req.setAttribute("groupId", groupId);

            // hien thi cac bai post da dang
            ArrayList<Post> posts = PostDAO.getInstance().selectByGroupId(gr.getGroupId());
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

            req.getRequestDispatcher("group.jsp").forward(req, resp);
        } else if (!GroupDAO.getInstance().checkCreator(user.getUserId(), gr.getGroupId())) {
            //account = false khi nguoi dang log khong phai la nguoi tao group

            req.setAttribute("account", false);
            if (GroupAccessDAO.getInstance().checkMember(user.getUserId(), gr.getGroupId()) == 1) { // admin = true && account = false thi hien nut Add People, Kick People, Post, Leave Group
                // admin = true khi admin la nguoi dang log
                req.setAttribute("admin", true);
                req.setAttribute("member", true);
                req.setAttribute("groupId", groupId);

                req.getRequestDispatcher("group.jsp").forward(req, resp);
            } else if (GroupAccessDAO.getInstance().checkMember(user.getUserId(), gr.getGroupId()) == 0) { // member = true thi hien nut Post, Leave Group
                // member = true khi nguoi dang log la member
                req.setAttribute("member", true);
                req.setAttribute("admin", false);
                req.setAttribute("groupId", groupId);

                // hien thi cac bai post da dang
                ArrayList<Post> posts = PostDAO.getInstance().selectByGroupId(gr.getGroupId());
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

                req.getRequestDispatcher("group.jsp").forward(req, resp);
            } else if (GroupAccessDAO.getInstance().checkMember(user.getUserId(), gr.getGroupId()) == -1) { // tuseday = true thi hien nut Join
                // tuesday = true khi nguoi dang log ko phai la member 
                req.setAttribute("tuesday", true);
                req.setAttribute("member", false);
                req.setAttribute("groupId", groupId);

                req.getRequestDispatcher("group.jsp").forward(req, resp);
            }
        }

    }

}
