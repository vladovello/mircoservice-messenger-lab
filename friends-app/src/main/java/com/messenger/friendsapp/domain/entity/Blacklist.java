package com.messenger.friendsapp.domain.entity;

import com.messenger.sharedlib.domain.ddd.DomainEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    private LocalDate additionDate;

    @NonNull private List<DomainEvent> domainEvents = new ArrayList<>();

    private Blacklist(@NonNull UUID requesterId, @NonNull Addressee addressee) {
        this.requesterId = requesterId;
        this.addressee = addressee;
    }

    public static @NonNull Blacklist createNewBlacklist(@NonNull UUID requesterId, @NonNull Addressee addressee) {
        var blacklist = new Blacklist(requesterId, addressee);
        blacklist.generateId();
        blacklist.setAdditionDate();
        return blacklist;
    }

    public static @NonNull Blacklist reconstructBlacklist(
            @NonNull UUID blacklistId,
            @NonNull UUID requesterId,
            @NonNull Addressee addressee,
            LocalDate additionDate
    ) {
        var blacklist = new Blacklist(requesterId, addressee);
        blacklist.setId(blacklistId);
        blacklist.setAdditionDate(additionDate);
        return blacklist;
    }

    public void clearEvents() {
        this.domainEvents.clear();
    }

    private void generateId() {
        id = UUID.randomUUID();
    }

    public void setAdditionDate() {
        this.additionDate = LocalDate.now();
    }
}
