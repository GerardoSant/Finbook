package WebSocket;

import Controller.util.JSONParser;
import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;


import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@WebSocket
public class EchoWebSocket {

    // Store sessions if you want to, for example, broadcast a message to all users
    private static final Queue<Session> sessions = new ConcurrentLinkedQueue<>();
    public static final Map<String, byte[]> messages = new HashMap<>();

    @OnWebSocketConnect
    public void connected(Session session) {
        sessions.add(session);
    }

    @OnWebSocketClose
    public void closed(Session session, int statusCode, String reason) {
        sessions.remove(session);
    }

    @OnWebSocketMessage
    public void message(Session senderSession, String message) throws IOException {
        messages.put(getId(message), getSign(message));
        for (Session session : sessions) {
            session.getRemote().sendString(message);
        }

    }

    private String getId(String message) {
        return new JSONParser(message).getString("id");
    }

    private byte[] getSign(String message) {
        return new JSONParser(message).getByteArray("sign");
    }

}