/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.dto.MessageDTO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Nguyen Huy Hoang
 */
public class ChatRestController extends HttpServlet {

    private MessageService messageService = new MessageService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String senderName = req.getParameter("sender");
        String receiverName = req.getParameter("receiver");
        String senderId = req.getParameter("senderId");
        String receiverId = req.getParameter("receiverId");
        List<MessageDTO> messages = messageService.getAllMessageBySenderAndReceiver(senderId, receiverId);

        // sysout ra de xem thu
        for (MessageDTO mess : messages) {
            System.out.println("tin nhan in ra browser:" + mess);
        }
        //---------------------

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(messages);
        
        System.out.println("json: " + json);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        PrintWriter printWriter = resp.getWriter();
        printWriter.print(json);
        printWriter.flush();
        

    }

}
