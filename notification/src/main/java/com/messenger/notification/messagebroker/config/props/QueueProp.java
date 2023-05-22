package com.messenger.notification.messagebroker.config.props;

import lombok.Data;

@Data
public class QueueProp {
    private String name;
    private boolean durable = true;
    private boolean exclusive = false;
    private boolean autoDelete = false;
}
