package net.zoltancsaszi.actionmonitor.dto;

import lombok.Getter;

import java.io.Serializable;

/**
 * DTO class to transfer monitoring events through JMS.
 *
 * @author Zoltan Csaszi
 */
@Getter
public class MonitoringEvent implements Serializable {

    private final long id;
    private final long timestamp;
    private final String eventType;

    public MonitoringEvent(long id, long timestamp, String eventType) {
        this.id = id;
        this.timestamp = timestamp;
        this.eventType = eventType;
    }

    public String getPayload() {
        return "timestamp="
                .concat(String.valueOf(timestamp))
                .concat(" :: a row with ID=")
                .concat(String.valueOf(id))
                .concat(" was ")
                .concat(eventType);
    }
}
