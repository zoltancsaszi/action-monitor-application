package net.zoltancsaszi.actionmonitor.service;

import lombok.extern.slf4j.Slf4j;
import net.zoltancsaszi.actionmonitor.dto.MonitoringEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;

/**
 * A service component that listening on a JMS message queue and forwards a converted message to WebSocket.
 *
 * @author Zoltan Csaszi
 */
@Slf4j
@Service
public class JmsListenerService {

    @Autowired
    private WebSocketService webSocketService;

    private CountDownLatch latch = new CountDownLatch(1);

    public CountDownLatch getLatch() {
        return latch;
    }

    @JmsListener(destination = "monitoring.queue")
    public void receive(MonitoringEvent monitoringEvent) {
        log.info("Received message='{}'", monitoringEvent.getId());

        webSocketService.sendMessage(monitoringEvent);

        latch.countDown();
    }
}