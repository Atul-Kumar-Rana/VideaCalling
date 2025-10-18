package com.backend.videocall;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SignalingHandler extends TextWebSocketHandler {
    private Map<String, List<WebSocketSession>> rooms = new ConcurrentHashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Map<String, Object> msg = new ObjectMapper().readValue(message.getPayload(), Map.class);
        String roomId = (String) msg.get("roomId");

        rooms.putIfAbsent(roomId, new ArrayList<>());
        List<WebSocketSession> sessions = rooms.get(roomId);

        // Forward message to all other peers
        for (WebSocketSession s : sessions) {
            if (!s.equals(session)) {
                s.sendMessage(message);
            }
        }

        if (!sessions.contains(session)) sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        rooms.values().forEach(list -> list.remove(session));
    }
}
