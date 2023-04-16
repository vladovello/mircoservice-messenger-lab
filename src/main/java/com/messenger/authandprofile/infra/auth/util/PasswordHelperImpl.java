package com.messenger.authandprofile.infra.auth.util;

import com.messenger.authandprofile.application.auth.util.PasswordHelper;
import com.messenger.authandprofile.domain.model.entity.User;
import com.messenger.authandprofile.domain.model.valueobject.Password;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordHelperImpl implements PasswordHelper {
    private final PasswordEncoder passwordEncoder;

    public PasswordHelperImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean doPasswordsMatch(@NonNull User user, @NonNull Password password) {
        var hashedPassword = passwordEncoder.encode(password.getValue());
        return hashedPassword.equals(user.getPassword().getValue());
    }
}
