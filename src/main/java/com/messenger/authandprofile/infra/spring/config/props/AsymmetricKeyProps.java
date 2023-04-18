package com.messenger.authandprofile.infra.spring.config.props;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AsymmetricKeyProps {
    private String privateKey;
    private String publicKey;
}
