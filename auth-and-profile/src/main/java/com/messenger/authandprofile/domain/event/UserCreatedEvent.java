package com.messenger.authandprofile.domain.event;

import com.messenger.authandprofile.domain.model.valueobject.BirthDate;
import com.messenger.authandprofile.domain.model.valueobject.FullName;
import com.messenger.sharedlib.domain.ddd.BaseDomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserCreatedEvent extends BaseDomainEvent {
    private UUID userId;
    private FullName fullName;
    private BirthDate birthDate;
    private UUID avatarId;

    @Override
    public String toString() {
        return "UserCreatedEvent{" +
                "userId=" + userId +
                ", fullName=" + fullName +
                ", birthDate=" + birthDate +
                ", avatarId=" + avatarId +
                '}';
    }
}
