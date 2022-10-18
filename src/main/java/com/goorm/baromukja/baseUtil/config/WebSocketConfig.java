package com.goorm.baromukja.baseUtil.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/18
 */

@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(reservationHandler(), "/reservationHandler");
    }

    @Bean
    public WebSocketHandler reservationHandler() {
        return new ReservationHandler();
    }
}