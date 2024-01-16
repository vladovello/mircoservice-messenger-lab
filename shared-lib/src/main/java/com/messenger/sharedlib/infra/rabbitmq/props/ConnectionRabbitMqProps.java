package com.messenger.sharedlib.infra.rabbitmq.props;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ConnectionRabbitMqProps {
    private String host;
    private String username;
    private String password;
}
