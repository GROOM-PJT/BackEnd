package com.goorm.baromukja.service;

import com.goorm.baromukja.repository.EmitterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/19
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;

    private final EmitterRepository emitterRepository;

    public void send(String username, String content) {
        // 로그인 한 유저의 SseEmitter 모두 가져오기
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllStartWithByUsername(username);
        sseEmitters.forEach(
                (key, emitter) -> {
                    sendToClient(emitter, key, content);
                }
        );
    }

    public SseEmitter subscribe(String username) {
        String id = username + "_" + System.currentTimeMillis();
        SseEmitter emitter = emitterRepository.save(id, new SseEmitter(DEFAULT_TIMEOUT));
//        emitter.onCompletion(() -> emitterRepository.deleteById(id));
//        emitter.onTimeout(() -> emitterRepository.deleteById(id));
//        sendToClient(emitter, id, "EventStream Created. [userId=" + username + "]");
        return emitter;
    }


    private void sendToClient(SseEmitter emitter, String id, String data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(id)
                    .name("sse")
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(id);
            throw new RuntimeException("연결 오류!");
        }
        log.info("emitter sent");
    }
}