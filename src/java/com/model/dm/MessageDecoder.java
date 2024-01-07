/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.dm;

import com.model.dto.MessageDTO;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 *
 * @author Nguyen Huy Hoang
 */
public class MessageDecoder implements Decoder.Text<MessageDTO> {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public MessageDTO decode(final String arg0) throws DecodeException {
        try {
            return objectMapper.readValue(arg0, MessageDTO.class);
        } catch (IOException e) {
            throw new DecodeException(arg0, "Unable to decode text to Message", e);
        }
    }

    @Override
    public boolean willDecode(final String arg0) {
        return arg0.contains("messageId") && arg0.contains("content");
    }

    @Override
    public void init(final EndpointConfig config) {
        
    }

    @Override
    public void destroy() {
        
    }

}
