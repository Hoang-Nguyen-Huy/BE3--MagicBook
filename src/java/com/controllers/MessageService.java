/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.model.dao.MessageDAO;
import com.model.dm.Message;
import com.model.dto.MessageDTO;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nguyen Huy Hoang
 */
public class MessageService {

    private String formatSqlDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
    
    private Message convertToEntity(MessageDTO messageDTO) {
        String messageId = messageDTO.getMessageId();
        String content = messageDTO.getContent();
        Date sentDate = messageDTO.getSentDate();
        Time sentTime = messageDTO.getSentTime();
        String receiverId = messageDTO.getReceiverId();
        String userId = messageDTO.getUserId();
        
        String formattedDate = formatSqlDate(sentDate);
        
        Message mess = new Message(messageId, content, Date.valueOf(formattedDate), sentTime, receiverId, userId);
        return mess;
    }

    private MessageDTO convertToDTO(Message mess) {
        String messageId = mess.getMessageId();
        String content = mess.getContent();
        Date sentDate = mess.getSentDate();
        Time sentTime = mess.getSentTime();
        String receiverId = mess.getReceiverId();
        String userId = mess.getUserId();
        MessageDTO messDTO = new MessageDTO(messageId, content, sentDate, sentTime, receiverId, userId);
        return messDTO;
    }

    public List<MessageDTO> getAllMessageBySenderAndReceiver(String sender, String receiver) {

        List<Message> listMessages = MessageDAO.getInstance().selectUserId(sender, receiver);
        List<MessageDTO> listMessageDTOs = new ArrayList<MessageDTO>();
        listMessages.stream().forEach(msg -> {
            MessageDTO messageDTO = convertToDTO(msg);
            listMessageDTOs.add(messageDTO);
        });
        return listMessageDTOs;

    }
    
    public void saveMessage(MessageDTO messageDTO) {
        Message mess = convertToEntity(messageDTO);
        System.out.println("tin nhan truoc khi dc luu vao db:" + mess);
        MessageDAO.getInstance().insert(mess);
    }

}
