package net.zoltancsaszi.actionmonitor.dto;

import org.springframework.stereotype.Component;

/**
 * Factory component that creates MonitoringEvent DTOs.
 *
 * @author Zoltan Csaszi
 */
@Component
public class MonitoringEventFactory {

    public MonitoringEvent createMonitoringEvent(long id, long timestamp, String eventType) {
        return new MonitoringEvent(id, timestamp, eventType);
    }
}
