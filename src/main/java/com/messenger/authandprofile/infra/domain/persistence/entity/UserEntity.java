package com.messenger.authandprofile.infra.domain.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(indexes = {@Index(columnList = "login"), @Index(columnList = "email"), @Index(columnList = "fullName")})
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

    @Column(nullable = false) private String firstName;

    @Column private String middleName;

    @Column(nullable = false) private String lastName;

    @Column(nullable = false) private String fullName;

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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        setFullName();
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
        setFullName();
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        setFullName();
    }

    private void setFullName() {
        fullName = firstName + " " + middleName + " " + lastName;
    }
}
