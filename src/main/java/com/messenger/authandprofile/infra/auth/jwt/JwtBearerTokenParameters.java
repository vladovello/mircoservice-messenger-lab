package com.messenger.authandprofile.infra.auth.jwt;

import com.messenger.authandprofile.infra.auth.jwt.exception.SigningKeyIsNotSetException;
import com.messenger.authandprofile.infra.auth.jwt.validator.LifetimeValidator;
import com.messenger.authandprofile.infra.auth.jwt.validator.SignatureValidator;
import com.messenger.authandprofile.infra.auth.jwt.validator.defaultimpl.BasicLifetimeValidator;
import com.messenger.authandprofile.infra.auth.jwt.validator.defaultimpl.BasicSignatureValidator;
import lombok.*;
import org.jetbrains.annotations.Contract;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.KeyPair;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;

@Getter
@ToString
@EqualsAndHashCode
public class JwtBearerTokenParameters {
    public JwtBearerTokenParameters() {
    }

    public JwtBearerTokenParameters(@NonNull Consumer<JwtBearerTokenParameters> options) {
        options.accept(this);
    }

    private static final int NANO_TO_MILLIS_DIV = 1_000_000;

    @Setter private String issuer = "localhost";
    @Setter private LocalTime lifespan = LocalTime.of(0, 5, 0);

    private SecretKey signingKey;
    private KeyPair keyPair;

    @Setter private SignatureValidator signatureValidator = new BasicSignatureValidator();
    @Setter private LifetimeValidator lifetimeValidator = new BasicLifetimeValidator();

    @Contract(" -> new")
    public @NonNull Date getExpirationDate() {
        return new Date(Calendar.getInstance().getTimeInMillis() + lifespan.toNanoOfDay() / NANO_TO_MILLIS_DIV);
    }

    public Key getSigningKey() {
        if (signingKey == null && keyPair == null) {
            return null;
        } else if (signingKey != null) {
            return signingKey;
        }

        return keyPair.getPrivate();
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
