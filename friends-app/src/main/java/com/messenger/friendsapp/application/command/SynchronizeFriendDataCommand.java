package com.messenger.friendsapp.application.command;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class SynchronizeFriendDataCommand {
    UUID friendId;
}
