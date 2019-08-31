package net.zoltancsaszi.actionmonitor.service;

import net.zoltancsaszi.actionmonitor.dto.Message;
import net.zoltancsaszi.actionmonitor.dto.MonitoringEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * A service component that sends messages through WebSocket.
 *
 * @author Zoltan Csaszi
 */
@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate webSocket;

    @Value("${websocket.destination}")
    private String destination;

    public void sendMessage(MonitoringEvent monitoringEvent) {
        Message message = new Message(monitoringEvent.getPayload());

        webSocket.convertAndSend(destination, message);
    }
}
