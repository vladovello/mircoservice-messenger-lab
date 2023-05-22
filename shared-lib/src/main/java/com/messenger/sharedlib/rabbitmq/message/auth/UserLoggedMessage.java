package com.messenger.sharedlib.rabbitmq.message.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoggedMessage {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private LocalDate birthDate;
    private UUID avatarId;

    @Override
    public String toString() {
        return "UserLoggedMessage{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", birthDate=" + birthDate +
                ", avatarId=" + avatarId +
                '}';
    }
}
