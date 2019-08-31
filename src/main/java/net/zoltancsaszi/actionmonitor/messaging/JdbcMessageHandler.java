package net.zoltancsaszi.actionmonitor.messaging;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.zoltancsaszi.actionmonitor.dto.MonitoringEvent;
import net.zoltancsaszi.actionmonitor.dto.MonitoringEventFactory;
import net.zoltancsaszi.actionmonitor.service.JmsSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Component that handles Jdbc messages from Spring Integration process flow.
 *
 * @author Zoltan Csaszi
 */
@Slf4j
@Component
@Setter
public class JdbcMessageHandler {

    @Autowired
    private MonitoringEventFactory monitoringEventFactory;

    @Autowired
    private JmsSenderService jmsSenderService;

    public void handleJdbcMessage(final Message<List<Map<String, Object>>> message) {
        final List<Map<String, Object>> payload = message.getPayload();

        for (Map<String, Object> columns : payload) {
            long id = (long) columns.get("PRIM_KEY");
            long timestamp = (long) columns.get("TIMESTAMP_");
            String eventType = (String) columns.get("EVENT_TYPE");

            final MonitoringEvent monitoringEvent =
                    monitoringEventFactory.createMonitoringEvent(id, timestamp, eventType);

            jmsSenderService.send(monitoringEvent);
        }
    }
}