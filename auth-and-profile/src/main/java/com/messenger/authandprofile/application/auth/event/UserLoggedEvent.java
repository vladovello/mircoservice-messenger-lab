package com.messenger.authandprofile.application.auth.event;

import com.messenger.authandprofile.domain.model.valueobject.BirthDate;
import com.messenger.authandprofile.domain.model.valueobject.FullName;
import com.messenger.sharedlib.ddd.domain.BaseDomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoggedEvent extends BaseDomainEvent {
    private UUID userId;
    private FullName fullName;
    private BirthDate birthDate;
    private UUID avatarId;

    @Override
    public String toString() {
        return "UserLoggedEvent{" +
                "userId=" + userId +
                ", fullName=" + fullName +
                ", birthDate=" + birthDate +
                ", avatarId=" + avatarId +
                '}';
    }
}
