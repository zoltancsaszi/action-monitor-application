package net.zoltancsaszi.actionmonitor.messaging;

import net.zoltancsaszi.actionmonitor.dto.MonitoringEvent;
import net.zoltancsaszi.actionmonitor.dto.MonitoringEventFactory;
import net.zoltancsaszi.actionmonitor.service.JmsSenderService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.mockito.Mockito.*;

/**
 * An example unit test contains mocking and uses Mockito.
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class JdbcMessageHandlerTest {

    private static final long PRIM_KEY = 0L;
    private static final long TIMESTAMP = 1L;
    private static final String EVENT_TYPE = "created";

    @Mock
    private JmsSenderService jmsSenderService;

    private MonitoringEvent monitoringEvent;

    @Mock
    private MonitoringEventFactory monitoringEventFactory;

    @Mock
    private Message<List<Map<String, Object>>> message;

    @InjectMocks
    private JdbcMessageHandler jdbcMessageHandler;

    @Before
    public void setup() {
        monitoringEvent = new MonitoringEvent(1L, System.currentTimeMillis(), "inserted");

        when(monitoringEventFactory.createMonitoringEvent(anyLong(), anyLong(), anyString()))
                .thenReturn(monitoringEvent);
    }

    @Test
    public void handleJdbcMessageTest() {

        // given a message containing an AuditLog record

        Map<String, Object> columns = new HashMap<>();

        columns.put("PRIM_KEY", PRIM_KEY);
        columns.put("TIMESTAMP_", TIMESTAMP);
        columns.put("EVENT_TYPE", EVENT_TYPE);

        List<Map<String, Object>> payload = new ArrayList<>();

        payload.add(columns);

        when(message.getPayload()).thenReturn(payload);

        // when the message is handled

        jdbcMessageHandler.handleJdbcMessage(message);

        // then the message sent to the jms message queue

        verify(jmsSenderService).send(monitoringEvent);
    }

    @Test
    public void handleJdbcMessageTest_whenRowsAreEmpty() {

        // given an empty message

        when(message.getPayload()).thenReturn(Collections.emptyList());

        // when the message is handled

        jdbcMessageHandler.handleJdbcMessage(message);

        // then no message sent to the jms message queue

        verify(jmsSenderService, never()).send(monitoringEvent);
    }
}