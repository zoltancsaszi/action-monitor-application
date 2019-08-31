package net.zoltancsaszi.actionmonitor.service;

import lombok.extern.slf4j.Slf4j;
import net.zoltancsaszi.actionmonitor.dto.MonitoringEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

/**
 * A service component that sends messages through JMS.
 *
 * @author Zoltan Csaszi
 */
@Slf4j
@Service
public class JmsSenderService {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void send(MonitoringEvent monitoringEvent) {
        log.info("Sending message='{}'", monitoringEvent.getId());

        jmsTemplate.convertAndSend("monitoring.queue", monitoringEvent);
    }
}