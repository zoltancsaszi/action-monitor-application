package net.zoltancsaszi.actionmonitor.dto;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * A Message payload DTO class for WebSocket communications.
 *
 * @author Zoltan Csaszi
 */
@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Message {

    private String payload;
}