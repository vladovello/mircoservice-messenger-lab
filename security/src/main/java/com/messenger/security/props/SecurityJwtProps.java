package com.messenger.security.props;

import lombok.Data;

@Data
public class SecurityJwtProps {
    private String[] permitAll;
    private String validationKey;
    private String rootPath;
}
