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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dell Latitude 7490
 */
public class SignUpController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // can hoan thien phan check form EMAIl
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String dob = req.getParameter("dob");
        String sex = req.getParameter("sex");
        String country = req.getParameter("country");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User user = new User(firstName, lastName, Date.valueOf(dob), sex, country, phone, email, password);

        if (!UserDAO.getInstance().checkDuplicateEmail(email)) {
            req.setAttribute("error", "Email is duplicated");
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
        }else if (!Validation.checkNameCountry(firstName) || !Validation.checkNameCountry(lastName)) {
            req.setAttribute("error", "Name is not valid");
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
        } else if (!Validation.checkNameCountry(country)) {
            req.setAttribute("error", "Country is not valid");
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
        } else if (!Validation.checkPassword(password)) {
            req.setAttribute("error", "Password must not be empty, no spaces and at least six characters");
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
        } else if (!Validation.checkPhone(phone)) {
            req.setAttribute("error", "Phone is not valid or duplicated");
            req.getRequestDispatcher("signup.jsp").forward(req, resp);
        } else {
            UserDAO.getInstance().insert(user);
            resp.sendRedirect("login");
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("signup.jsp").forward(req, resp);
    }

}
