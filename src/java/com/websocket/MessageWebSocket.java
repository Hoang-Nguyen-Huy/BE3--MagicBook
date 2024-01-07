/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.websocket;

import com.controllers.ChatService;
import com.controllers.MessageService;
import com.model.dm.MessageDecoder;
import com.model.dm.MessageEncoder;
import com.model.dto.MessageDTO;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Nguyen Huy Hoang
 */
@ServerEndpoint(value = "/message/{username}/{userId}", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
public class MessageWebSocket {

    private Session session;
    private String username;
    private String userId;

    private ChatService chatService = new ChatService();
    private MessageService messageService = new MessageService();

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ChatService getChatService() {
        return chatService;
    }

    public void setChatService(ChatService chatService) {
        this.chatService = chatService;
    }

    public MessageService getMessageService() {
        return messageService;
    }

    public void setMessageService(MessageService messageService) {
        this.messageService = messageService;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @OnOpen
    public void onOpen(@PathParam("username") String username, @PathParam("userId") String userId, Session session) {
        if (chatService.register(this)) {
            this.session = session;
            this.username = username;
            this.userId = userId;
            String receiver = "all";
            MessageDTO messResponse = new MessageDTO(null, "[P]open", null, null, receiver, this.userId);
            System.out.println(messResponse);
            chatService.sendMessageToAllUsers(messResponse);
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }

    @OnMessage
    public void onMessage(MessageDTO message, Session session) {
        System.out.println(message);
        chatService.sendMessageToOneUser(message);
        messageService.saveMessage(message);
    }
    
    @OnClose
    public void onClose(Session session) {
        if (chatService.close(this)) {
            String receiver = "all";
            MessageDTO mssgResponse = new MessageDTO(null, "[P]close", null, null, receiver, this.userId);
            chatService.sendMessageToAllUsers(mssgResponse);
        }
    }

}
