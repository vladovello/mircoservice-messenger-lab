package com.messenger.chat.application;

import java.util.UUID;


public interface ChatUser {
    UUID getRequesterId();
    UUID getChatId();
}
