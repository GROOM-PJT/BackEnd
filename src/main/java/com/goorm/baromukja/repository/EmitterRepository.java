package com.goorm.baromukja.repository;

/**
 * @Author : Jeeseob
 * @CreateAt : 2022/10/19
 */

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class EmitterRepository {

    public final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

    public SseEmitter save(String id, SseEmitter sseEmitter) {
        emitters.put(id, sseEmitter);
        return sseEmitter;
    }

    public Map<String, SseEmitter> findAllStartWithByUsername(String id) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(id))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void deleteById(String id) {
        emitters.remove(id);
    }
}
