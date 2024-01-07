/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.dm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.dto.MessageDTO;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author Nguyen Huy Hoang
 */
public class MessageEncoder implements Encoder.Text<MessageDTO> {

    private static ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String encode(final MessageDTO arg0) throws EncodeException {
        try {
            return objectMapper.writeValueAsString(arg0);
        } catch (JsonProcessingException e) {
            throw new EncodeException(arg0, "Unable to encode message", e);
        }
    }

    @Override
    public void init(final EndpointConfig config) {
        
    }

    @Override
    public void destroy() {
        
    }

}
