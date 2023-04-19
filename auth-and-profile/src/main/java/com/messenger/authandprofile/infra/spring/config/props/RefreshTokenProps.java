package com.messenger.authandprofile.infra.spring.config.props;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalTime;

@Getter
@Setter
@ToString
public class RefreshTokenProps {
    private SignatureAlgorithm signingAlgorithm = SignatureAlgorithm.ES512;
    private LocalTime lifespan = LocalTime.of(12, 0, 0);
}
