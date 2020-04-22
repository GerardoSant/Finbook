package SparkApp;

import Controller.util.JSONParser;
import org.eclipse.jetty.websocket.api.*;
import org.eclipse.jetty.websocket.api.annotations.*;


import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@WebSocket
public class SignWebSocket {

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
        saveMessage(message);
        for (Session session : sessions) {
            sendMessageToSessions(message, session);
        }

    }

    private void saveMessage(String message) {
        messages.put(getId(message), getSign(message));
    }

    private void sendMessageToSessions(String message, Session session) throws IOException {
        session.getRemote().sendString(message);
    }

    private String getId(String message) {
        return new JSONParser(message).getString("id");
    }

    private byte[] getSign(String message) {
        return new JSONParser(message).getByteArray("sign");
    }

}