package com.messenger.sharedlib.infra.props;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ServiceIntegrationProps {
    private String baseUrl;
    private String rootPath;
    private int timeout;
    private String apiKey;
}
