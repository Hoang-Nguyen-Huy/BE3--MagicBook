/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.model.dao.UserDAO;
import com.model.dm.User;
import com.utils.Validation;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
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
public class UpdateProfileController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String avatar = "";

        User upUser = new User();
        String id = "";
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("id")) {
                id = cookie.getValue();
                upUser = UserDAO.getInstance().checkAccessToHome(id);
                break;
            }
        }

        String newEmail = "";
        String newPassword = "";
        boolean upPassword = false;

        System.out.println(avatar);

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
                    String value = item.getString();
                    System.out.println("name: " + name + " value: " + value);

                    if ("email".equals(name)) {
                        if (!UserDAO.getInstance().checkDuplicateEmail(value)) {
                            req.setAttribute("error", "Email is duplicated");
                            req.getRequestDispatcher("updateProfile.jsp").forward(req, resp);
                        } else if (!value.isEmpty()) {
                            upUser.setEmail(value);
                            newEmail = value;
                        }
                    }
                    if ("firstName".equals(name)) {
                        if (!Validation.checkUpNameCountry(value)) {
                            req.setAttribute("error", "Name is not valid");
                            req.getRequestDispatcher("updateProfile.jsp").forward(req, resp);
                        } else if (!value.isEmpty()) {
                            upUser.setFirstName(value);
                        }
                    }
                    if ("lastName".equals(name)) {
                        if (!Validation.checkUpNameCountry(value)) {
                            req.setAttribute("error", "Name is not valid");
                            req.getRequestDispatcher("updateProfile.jsp").forward(req, resp);
                        } else if (!value.isEmpty()) {
                            upUser.setLastName(value);
                        }
                    }
                    if ("country".equals(name)) {
                        if (!Validation.checkUpNameCountry(value)) {
                            req.setAttribute("error", "Country is not valid");
                            req.getRequestDispatcher("updateProfile.jsp").forward(req, resp);
                        } else if (!value.isEmpty()) {
                            upUser.setCountry(value);
                        }
                    }
                    if ("password".equals(name)) {
                        if (!Validation.checkUpPassword(value)) {
                            req.setAttribute("error", "Password must not be empty, no spaces and at least six characters");
                            req.getRequestDispatcher("updateProfile.jsp").forward(req, resp);
                        } else if (!value.isEmpty()) {
                            upPassword = true;
                            upUser.setPassword(value);
                            newPassword = value;
                        }
                    }
                    if ("phone".equals(name)) {
                        if (!Validation.checkUpPhone(value)) {
                            req.setAttribute("error", "Phone is not valid or duplicated");
                            req.getRequestDispatcher("updateProfile.jsp").forward(req, resp);
                        } else if (!value.isEmpty()) {
                            upUser.setPhone(value);
                        }
                    }
                    if ("dob".equals(name)) {
                        if (!value.isEmpty()) {
                            upUser.setDob(Date.valueOf(value));
                        }
                    }
                    if ("sex".equals(name)) {
                        if (!value.equals(upUser.getSex())) {
                            upUser.setSex(value);
                        }
                    }
                } else {
                    if ("avatar".equals(item.getFieldName())) {
                         avatar = item.getName();
                        if (avatar.equals("")) {
                            break;
                        } else {
                            Path path = Paths.get(avatar);
                            String storePath = servletContext.getRealPath("/avatarUser");
                            File uploadFile = new File(storePath + "/" + path.getFileName());
                            item.write(uploadFile);
                            System.out.println(storePath + "/" + path.getFileName());
                            upUser.setAvatar("avatarUser\\" + path.getFileName());
                        }
                    }
                }
            }
        } catch (Exception e) {

        }

        UserDAO.getInstance().updateProfile(upUser, upPassword);
        if (!newEmail.equals("") || !newPassword.equals("")) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("id")) {
                    cookie.setMaxAge(0);
                    resp.addCookie(cookie);
                    break;
                }
            }
            resp.sendRedirect("login");
        } else {
            resp.sendRedirect("profile");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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
        if (user != null) {
            req.setAttribute("userId", id);
            req.getRequestDispatcher("updateProfile.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath());
        }

    }

}
