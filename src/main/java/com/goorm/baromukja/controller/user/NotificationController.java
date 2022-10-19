package com.goorm.baromukja.controller.user;

import com.goorm.baromukja.baseUtil.config.JwtProperties;
import com.goorm.baromukja.baseUtil.config.service.JwtService;
import com.goorm.baromukja.service.NotificationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.links.Link;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/19
 */

@Api(tags = "00-1. 알림 서버 연결 - user")
@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notification")
public class NotificationController {
        private final NotificationService notificationService;
        private final JwtService jwtService;

        /**
         * @title 로그인 한 유저 sse 연결
         */
        @GetMapping(value = "/reservation", produces = "text/event-stream")
        @ApiOperation(value = "알림 구독 설정", notes = "알림 자동 구독")
        public SseEmitter subscribe(HttpServletRequest request) {
            String username = jwtService.decode(request.getHeader("Authorization").replace(JwtProperties.TOKEN_PREFIX, ""));
            return notificationService.subscribe(username);
        }
    }

