/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.model.dao.GroupDAO;
import com.model.dao.UserDAO;
import com.model.dm.Group;
import com.model.dm.User;
import com.utils.Validation;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
public class UpdateGroupController extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        User user = new User();
        String id = ""; // id lay tu cookie
        String urlGroupId = req.getParameter("groupid");
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
        
        Group upGr = GroupDAO.getInstance().checkIdFromUrl(urlGroupId);  //update Group
        String avatar = "";
        
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
                    System.out.println("name: " + name + "value: " + value);

                    if ("groupName".equals(name)) {
                        if (!Validation.checkUpNameCountry(value)) {
                            req.setAttribute("error", "This name is not valid");
                            req.getRequestDispatcher("updateGroup.jsp").forward(req, resp);
                        } else if (!value.isEmpty()){
                            upGr.setName(value);
                        }
                    }
                } else {
                    if ("avatar".equals(item.getFieldName())) {
                        avatar = item.getName();
                        if (avatar.equals("")) {
                            break;
                        } else {
                            Path path = Paths.get(avatar);
                            String storePath = servletContext.getRealPath("/avatarGroup");
                            File uploadFile = new File(storePath + "/" + path.getFileName());
                            item.write(uploadFile);
                            System.out.println(storePath + "/" + path.getFileName());
                            upGr.setAvatar("avatarGroup\\" + path.getFileName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        GroupDAO.getInstance().update(upGr);
        
        resp.sendRedirect("group?groupid=" + urlGroupId);  
        
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        User user = null;
        String id = "";
        String urlGroupId = req.getParameter("groupid");
        if (urlGroupId == null) {
            resp.sendRedirect("home");
            return;
        }
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
            req.getRequestDispatcher("updateGroup.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath());
        }
        
    }
    
}
