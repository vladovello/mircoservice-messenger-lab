package com.messenger.security.jwt;

import lombok.Data;

/*
 * Сделать так, чтобы сервис валидации в конфигах получал все возможные для данного сервиса ключи валидации и доп конфиги для валидации
 * При проверке токена, будет происходить перебор по всем ключам и, первый ключ, при котором токен прошёл проверку, будет считаться валидным
 */
@Data
public class SecurityJwtProps {
    private String[] permitAll;
    private ValidationParams validationParams;
    private String rootPath;
}
