package com.messenger.security.jwt;

import lombok.Data;

import java.time.LocalTime;

/*
 * Сделать так, чтобы сервис валидации в конфигах получал все возможные для данного сервиса ключи валидации и доп конфиги для валидации
 * При проверке токена, будет происходить перебор по всем ключам и, первый ключ, при котором токен прошёл проверку, будет считаться валидным
 */
@Data
public class SecurityJwtProps {
    private String[] permitAll;
    private String validationKey;
    private LocalTime accessExp;
    private String rootPath;

    public ValidationParams getValidationParams() {
        return new ValidationParams(validationKey, accessExp);
    }
}
