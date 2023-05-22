package com.messenger.sharedlib.integration.props;

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
