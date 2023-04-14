package com.messenger.authandprofile.infra.domain.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        indexes = {
                @Index(columnList = "login"),
                @Index(columnList = "email"),
                @Index(columnList = "fullName")
        }
)
@Getter
@Builder(setterPrefix = "with")
@AllArgsConstructor
public class UserEntity {
    @Id
    @Column(nullable = false)
    @Setter
    private UUID id;

    @Column(nullable = false)
    @Setter
    private String login;

    @Column(nullable = false)
    @Setter
    private String email;

    @Column(nullable = false)
    @Setter
    private String firstName;

    @Column
    @Setter
    private String middleName;

    @Column(nullable = false)
    @Setter
    private String lastName;

    @Column(nullable = false)
    @Formula("concat(firstName, ' ', middleName, ' ', lastName)")
    private String fullName;

    @Column(nullable = false)
    @Setter
    private String password;

    @Column(nullable = false)
    @Setter
    private LocalDate registrationDate;

    @Column
    @Setter
    private String phoneNumber;

    @Column
    @Setter
    private LocalDate birthDate;

    @Column
    @Setter
    private String city;

    @Column
    @Setter
    private UUID avatar;

    public UserEntity() {
        // For JPA
    }
}
