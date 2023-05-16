package com.messenger.chat.domain.user.repository;

import com.messenger.chat.domain.user.User;

import java.util.UUID;

// INFO: Менять данные через этот репозиторий нельзя, так как объявив такой интерфейс мы неявно покажем, что сервис чатов может менять
//  пользователей, что неверно. Поэтому, для синхронизации данных с сервисом профилей можно использовать инфраструктурный
//  сервис, о котором ни в домене, ни в слое приложения знать не будут.
public interface UserRepository {
    User getUser(UUID userId);
}
