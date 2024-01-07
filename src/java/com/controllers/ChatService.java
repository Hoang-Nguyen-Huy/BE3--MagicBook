/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controllers;

import com.model.dto.MessageDTO;
import com.websocket.MessageWebSocket;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.websocket.EncodeException;

/**
 *
 * @author Dell Latitude 7490
 */
public class ChatService {

    protected static final Set<MessageWebSocket> messageWebSockets = new CopyOnWriteArraySet<>();

    public boolean register(MessageWebSocket messageWebSocket) {
        return messageWebSockets.add(messageWebSocket);
    }

    public boolean close(MessageWebSocket messageWebSocket) {
        return messageWebSockets.remove(messageWebSocket);
    }

    public boolean isUserOnline(String username) {
        for (MessageWebSocket messageWebSocket : messageWebSockets) {
            if (messageWebSocket.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public void sendMessageToAllUsers(MessageDTO message) {
        messageWebSockets.stream().forEach(messageWebSocket -> {
            try {
                messageWebSocket.getSession().getBasicRemote().sendObject(message);
            } catch (IOException | EncodeException e) {
                e.printStackTrace();
            }
        });
    }

    public void sendMessageToOneUser(MessageDTO message) {
        if (message.getReceiverId() != null) {
            messageWebSockets.stream()
                    .filter(messageWebSocket -> !messageWebSocket.getUserId().equals(message.getReceiverId()))
                    .forEach(messageWebSocket -> {
                        try {
                            messageWebSocket.getSession().getBasicRemote().sendObject(message);
                        } catch (IOException | EncodeException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

}
