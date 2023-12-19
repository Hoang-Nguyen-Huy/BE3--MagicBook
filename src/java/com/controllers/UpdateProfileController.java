/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.model.dao.UserDAO;
import com.model.dm.User;
import com.utils.Validation;
import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nguyen Huy Hoang
 */
public class UpdateProfileController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String dob = req.getParameter("dob");
        String sex = req.getParameter("sex");
        String country = req.getParameter("country");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User upUser = null;
        String id = "";
        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("id")) {
                id = cookie.getValue();
                upUser = UserDAO.getInstance().checkAccessToHome(id);
                break;
            }
        }

        if (!UserDAO.getInstance().checkDuplicateEmail(email)) {
            req.setAttribute("error", "Email is duplicated");
            req.getRequestDispatcher("updateProfile.jsp").forward(req, resp);
        } else if (!Validation.checkUpNameCountry(firstName) || !Validation.checkUpNameCountry(lastName)) {
            req.setAttribute("error", "Name is not valid");
            req.getRequestDispatcher("updateProfile.jsp").forward(req, resp);
        } else if (!Validation.checkUpNameCountry(country)) {
            req.setAttribute("error", "Country is not valid");
            req.getRequestDispatcher("updateProfile.jsp").forward(req, resp);
        } else if (!Validation.checkUpPassword(password)) {
            req.setAttribute("error", "Password must not be empty, no spaces and at least six characters");
            req.getRequestDispatcher("updateProfile.jsp").forward(req, resp);
        } else if (!Validation.checkUpPhone(phone)) {
            req.setAttribute("error", "Phone is not valid or duplicated");
            req.getRequestDispatcher("updateProfile.jsp").forward(req, resp);
        } else {
            String newEmail = "";
            String newPassword = "";
            boolean upPassword = false;
            if (!firstName.isEmpty()) {
                upUser.setFirstName(firstName);
            }
            if (!lastName.isEmpty()) {
                upUser.setLastName(lastName);
            }
            if (!dob.isEmpty()) {
                upUser.setDob(Date.valueOf(dob));
            }
            if (!sex.equals(upUser.getSex())) {
                upUser.setSex(sex);
            }
            if (!country.isEmpty()) {
                upUser.setCountry(country);
            }
            if (!phone.isEmpty()) {
                upUser.setPhone(phone);
            }
            if (!email.isEmpty()) {
                upUser.setEmail(email);
                newEmail = email;
            }
            if (!password.isEmpty()) {
                upPassword = true;
                upUser.setPassword(password);
                newPassword = password;
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

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = null;
        String id = "";
        Cookie[] cookies = req.getCookies();
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
