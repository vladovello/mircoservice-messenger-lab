package com.messenger.authandprofile.application.auth.util;

import com.messenger.authandprofile.domain.model.entity.User;
import com.messenger.authandprofile.domain.model.valueobject.Password;

public interface PasswordHelper {
    boolean doPasswordsMatch(User user, Password password);
}
