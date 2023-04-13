package com.messenger.authandprofile.infra.auth.jwt;

import com.messenger.authandprofile.infra.auth.jwt.exception.SigningKeyIsNotSetException;
import com.messenger.authandprofile.infra.auth.jwt.validator.LifetimeValidator;
import com.messenger.authandprofile.infra.auth.jwt.validator.SignatureValidator;
import com.messenger.authandprofile.infra.auth.jwt.validator.defaultimpl.BasicLifetimeValidator;
import com.messenger.authandprofile.infra.auth.jwt.validator.defaultimpl.BasicSignatureValidator;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.Contract;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.KeyPair;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

@Data
public class JwtBearerTokenParameters {
    private static final int NANO_TO_MILLIS_DIV = 1_000_000;

    private String issuer = "localhost";
    private LocalTime lifespan = LocalTime.of(0, 5, 0);

    @Setter(value = AccessLevel.PRIVATE) private SecretKey signingKey;
    @Setter(value = AccessLevel.PRIVATE) private KeyPair keyPair;

    private SignatureValidator signatureValidator = new BasicSignatureValidator();
    private LifetimeValidator lifetimeValidator = new BasicLifetimeValidator();

    @Contract(" -> new")
    public @NonNull Date getExpirationDate() {
        return new Date(Calendar.getInstance().getTimeInMillis() + lifespan.toNanoOfDay() / NANO_TO_MILLIS_DIV);
    }

    public void setSymmetricKey(SecretKey secretKey) {
        signingKey = secretKey;
        keyPair = null;
    }

    public void setAsymmetricKey(KeyPair keyPair) {
        this.keyPair = keyPair;
        signingKey = null;
    }

    public Key getVerificationKey() {
        if (signingKey != null) return signingKey;
        if (keyPair != null) return keyPair.getPublic();

        throw new SigningKeyIsNotSetException();
    }
}
