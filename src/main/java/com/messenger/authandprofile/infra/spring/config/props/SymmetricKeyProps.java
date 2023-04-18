package com.messenger.authandprofile.infra.spring.config.props;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SymmetricKeyProps {
    private String secretKey;
}
