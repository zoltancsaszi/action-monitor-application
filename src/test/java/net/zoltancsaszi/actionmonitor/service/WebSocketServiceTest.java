package net.zoltancsaszi.actionmonitor.service;

import net.zoltancsaszi.actionmonitor.dto.Message;
import net.zoltancsaszi.actionmonitor.dto.MonitoringEvent;
import net.zoltancsaszi.actionmonitor.dto.MonitoringEventFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class WebSocketServiceTest {

    @Mock
    private SimpMessagingTemplate simpMessagingTemplate;
    @InjectMocks
    private WebSocketService webSocketService;
    @InjectMocks
    private MonitoringEventFactory monitoringEventFactory;

    @Test
    public void sendMessageTest() {

        //given a monitoring event

        final long timestamp = 1L;
        final long id = 1L;
        final String eventType = "created";

        final MonitoringEvent monitoringEvent = monitoringEventFactory.createMonitoringEvent(
                id, timestamp, eventType);

        ReflectionTestUtils.setField(webSocketService, "destination", "test");

        // when we send the monitoring event to the WebSocketService

        webSocketService.sendMessage(monitoringEvent);

        //then it will send a converted message payload through WebSocket, exactly once

        Message message = new Message(monitoringEvent.getPayload());

        verify(simpMessagingTemplate).convertAndSend(eq("test"), eq(message));
    }

}