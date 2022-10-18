package com.goorm.baromukja.baseUtil.config;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/18
 */

public class ReservationHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // ...
    }
}
