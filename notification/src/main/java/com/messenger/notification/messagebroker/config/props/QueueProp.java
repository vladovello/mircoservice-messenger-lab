package com.messenger.notification.messagebroker.config.props;

import lombok.Data;

import java.util.Map;

@Data
public class QueueProp {
    private String name;
    private boolean durable = true;
    private boolean exclusive = false;
    private boolean autoDelete = false;
    private Map<String, Object> arguments;
}
