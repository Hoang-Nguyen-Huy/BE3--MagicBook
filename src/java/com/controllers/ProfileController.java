/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.model.dao.CommentDAO;
import com.model.dao.FriendshipDAO;
import com.model.dao.LikeDAO;
import com.model.dao.PostDAO;
import com.model.dao.UserDAO;
import com.model.dm.Friendship;
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
                    if ("visibility".equals(item.getFieldName())) {
                        post.setVisibility(item.getString("UTF-8"));
                        System.out.println(item.getString("UTF-8"));
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
            System.out.println(post.toString());
            PostDAO.getInstance().insert(post);
            resp.sendRedirect("profile?id=" + userId);
        } else {
            resp.sendRedirect("profile?id=" + userId);
        }
        //-----------------------

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

                    // hien thi cac bai post da dang
                    ArrayList<Post> posts = PostDAO.getInstance().selectByUserId(u.getUserId());
                    HashMap<User, Post> map = new HashMap<>();

                    for (Post p : posts) {
                        map.put(UserDAO.getInstance().selectById(p.getUserId()), p);
                    }

                    Set<User> keyMap = map.keySet();

                    for (User u1 : keyMap) {
                        u1.setUserId(Util.encryptPassword(u1.getUserId()));
                    }

                    for (Post p : posts) {
                        p.setPostId(Util.encryptPassword(p.getPostId()));
                    }

                    req.setAttribute("post", map);
                    //------------------------------

                    req.getRequestDispatcher("profile.jsp").forward(req, resp);
                }
            }
        }

    }

}
