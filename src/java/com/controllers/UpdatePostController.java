/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.model.dao.PostDAO;
import com.model.dao.UserDAO;
import com.model.dm.Post;
import com.model.dm.User;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
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
public class UpdatePostController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = new User();
        String id = ""; // id lay tu cookie
        String urlPostId = req.getParameter("postid");
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
        Post upPost = PostDAO.getInstance().checkIdFromUrl(urlPostId);
        String postFile = "";

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

                    if ("content".equals(name)) {
                        if (!value.isEmpty()) {
                            upPost.setContent(value.trim());
                            System.out.println(value);
                            // de lay thoi gian thuc cua bai dang 
                            Calendar calendar = Calendar.getInstance();
                            upPost.setPostDate(new Date(calendar.getTime().getTime()));
                            upPost.setPostTime(new Time(calendar.getTime().getTime()));
                            System.out.println(upPost.getPostDate());
                            System.out.println(upPost.getPostTime());
                            System.out.println(upPost.getUserId());
                        }
                    }
                    if ("visibility".equals(item.getFieldName())) {
                        if (!value.equals(upPost.getVisibility())) {
                            upPost.setVisibility(item.getString("UTF-8"));
                            // de lay thoi gian thuc cua bai dang 
                            Calendar calendar = Calendar.getInstance();
                            upPost.setPostDate(new Date(calendar.getTime().getTime()));
                            upPost.setPostTime(new Time(calendar.getTime().getTime()));
                            System.out.println(item.getString("UTF-8"));
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
                            upPost.setFile("postContent\\" + path.getFileName());
                            // de lay thoi gian thuc cua bai dang 
                            Calendar calendar = Calendar.getInstance();
                            upPost.setPostDate(new Date(calendar.getTime().getTime()));
                            upPost.setPostTime(new Time(calendar.getTime().getTime()));
                            System.out.println(upPost.getPostDate());
                            System.out.println(upPost.getPostTime());
                        }
                    }
                }
            }
        } catch (Exception e) {

        }

        if (upPost.getContent() != null) {
            System.out.println(upPost.toString());
            PostDAO.getInstance().update(upPost);
            resp.sendRedirect("home");
        } else {
            resp.sendRedirect("home");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = null;
        String id = "";
        String urlPostId = req.getParameter("postid");
        if (urlPostId == null) {
            resp.sendRedirect("home");
            return;
        }
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
            req.getRequestDispatcher("updatePost.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath());
        }

    }

}
