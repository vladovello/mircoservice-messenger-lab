package com.messenger.friendsapp.domain.aggregate;

import com.messenger.friendsapp.domain.entity.Addressee;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.UUID;

/*
 * Если добавляешь взаимного друга в блэклист, он остаётся в друзях, но при запросе списка друзей, он в нём не появится.
 * У заблокированного человека, при запросе друзей, будет проверяться, кто добавил его в блок и блокировщиков в списке не будет.
 */
@Getter
@Setter(value = AccessLevel.PRIVATE)
public class Blacklist {
    @NonNull private UUID id;
    @NonNull private UUID requesterId;
    @NonNull private Addressee addressee;

    private Blacklist(@NonNull UUID requesterId, @NonNull Addressee addressee) {
        this.requesterId = requesterId;
        this.addressee = addressee;
    }

    public static @NonNull Blacklist createNewBlacklist(@NonNull UUID requesterId, @NonNull Addressee addressee) {
        var blacklist = new Blacklist(requesterId, addressee);
        blacklist.generateId();
        return blacklist;
    }

    public static @NonNull Blacklist reconstructBlacklist(
            @NonNull UUID blacklistId,
            @NonNull UUID requesterId,
            @NonNull Addressee addressee
    ) {
        var blacklist = new Blacklist(requesterId, addressee);
        blacklist.setId(blacklistId);
        return blacklist;
    }

    private void generateId() {
        id = UUID.randomUUID();
    }
}