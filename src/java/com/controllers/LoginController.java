/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.model.dao.UserDAO;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dell Latitude 7490
 */
public class LoginController extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String userId = UserDAO.getInstance().login(email, password);
        if (!userId.equals("")) {
            // lấy id là cookie.name, còn userId là cookie.value
            Cookie cookie = new Cookie("id", userId); 
            resp.addCookie(cookie);
            resp.sendRedirect("home");
        } else {
            req.setAttribute("error", "Wrong email or password");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
        
    }
       
}
