/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.model.dao.CommentDAO;
import com.model.dao.LikeDAO;
import com.model.dao.PostDAO;
import com.model.dao.UserDAO;
import com.model.dm.Like;
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
 * @author Dell Latitude 7490
 */
public class HomeController extends HttpServlet {

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

        // like post-----------------
        String likePostId = req.getParameter("likedpostId");
        System.out.println(likePostId);
        if (likePostId != null) {
            Post likedPost = PostDAO.getInstance().checkIdFromUrl(likePostId);
            Like likeStatus = LikeDAO.getInstance().checkLikeStatus(user.getUserId(), likedPost.getPostId());

            if (likeStatus != null) { // neu da like roi thi huy like
                LikeDAO.getInstance().delete(likeStatus);
                System.out.println("da huy like");
            } else {
                Like newLike = new Like();
                // de lay thoi gian thuc cua bai dang 
                Calendar calendar = Calendar.getInstance();
                newLike.setLikeDate(new Date(calendar.getTime().getTime()));
                newLike.setLikeTime(new Time(calendar.getTime().getTime()));
                System.out.println(newLike.getLikeDate());
                System.out.println(newLike.getLikeTime());

                newLike.setUserId(user.getUserId());
                newLike.setPostId(likedPost.getPostId());

                LikeDAO.getInstance().insert(newLike);

                System.out.println("dalike");
            }
        }
        //---------------------------

        //delete post --------------------
        String action = req.getParameter("action");
        String delPostId = req.getParameter("postId");
        Post delPost = PostDAO.getInstance().checkIdFromUrl(delPostId);

        if ("delete".equals(action) && delPost != null) {
            System.out.println(delPost.toString());
            LikeDAO.getInstance().deleteByPostId(delPost.getPostId());
            CommentDAO.getInstance().deleteByPostId(delPost.getPostId());
            PostDAO.getInstance().delete(delPost);
            System.out.println(action);
        } else if ("cancelDelete".equals(action) && delPost != null) {
            System.out.println(action);
            System.out.println(delPost.toString());
        }
        //------------------------

        // up Post -----------------
        if (post.getContent() != null || post.getFile() != null) {
            System.out.println(post.toString());
            PostDAO.getInstance().insert(post);
            resp.sendRedirect("home");
        } else {
            resp.sendRedirect("home");
        }
        //-----------------------

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

            // dang post
            ArrayList<Post> posts = PostDAO.getInstance().selectAll();
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
            // -----------------------

            req.getRequestDispatcher("home.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath());
        }

    }

}
