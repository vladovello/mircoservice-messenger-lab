package com.messenger.friendsapp.infra.persistence.mapper;

import com.messenger.friendsapp.domain.entity.Addressee;
import com.messenger.friendsapp.domain.entity.Blacklist;
import com.messenger.friendsapp.domain.valueobject.FullName;
import com.messenger.friendsapp.infra.persistence.entity.BlacklistEntity;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;

public final class BlacklistEntityMapper {
    private BlacklistEntityMapper() {
    }

    public static @NonNull Blacklist mapToDomainModel(@NonNull BlacklistEntity blacklistEntity) {
        return Blacklist.reconstructBlacklist(
                blacklistEntity.getId(),
                blacklistEntity.getRequesterId(),
                Addressee.createAddressee(
                        blacklistEntity.getAddresseeId(),
                        new FullName(
                                blacklistEntity.getAddresseeFirstName(),
                                blacklistEntity.getAddresseeMiddleName(),
                                blacklistEntity.getAddresseeLastName()
                        )
                ),
                blacklistEntity.getAdditionDate()
        );
    }

    @Contract("_ -> new")
    public static @NonNull BlacklistEntity mapToEntity(@NonNull Blacklist blacklist) {
        var be = new BlacklistEntity(
                blacklist.getId(),
                blacklist.getRequesterId(),
                blacklist.getAddressee().getId(),
                blacklist.getAddressee().getFullName().getFirstName(),
                blacklist.getAddressee().getFullName().getMiddleName(),
                blacklist.getAddressee().getFullName().getLastName(),
                blacklist.getAddressee().getFullName().getName(),
                blacklist.getAdditionDate()
        );
        be.setDomainEvents(blacklist.getDomainEvents());

        return be;
    }
}
