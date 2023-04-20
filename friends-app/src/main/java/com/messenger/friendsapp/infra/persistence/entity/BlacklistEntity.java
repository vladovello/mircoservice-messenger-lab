package com.messenger.friendsapp.infra.persistence.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        indexes = {@Index(columnList = "requesterId,addresseeId", unique = true)}
)
@Getter
public class BlacklistEntity {
    @Id
    @Column(nullable = false)
    @Setter
    private UUID id;

    @Column(nullable = false)
    @Setter
    private UUID requesterId;

    @Column(nullable = false)
    @Setter
    private UUID addresseeId;

    @Column(nullable = false) private String addresseeFirstName;

    @Column private String addresseeMiddleName;

    @Column(nullable = false) private String addresseeLastName;
    @Column(nullable = false) private String addresseeFullName;

    @Column
    private LocalDate additionDate;

    @Column
    @Setter
    private LocalDate deletionDate;

    public BlacklistEntity(
            UUID id,
            UUID requesterId,
            UUID addresseeId,
            String addresseeFirstName,
            String addresseeMiddleName,
            String addresseeLastName,
            String addresseeFullName,
            LocalDate additionDate
    ) {
        this.id = id;
        this.requesterId = requesterId;
        this.addresseeId = addresseeId;
        this.addresseeFirstName = addresseeFirstName;
        this.addresseeMiddleName = addresseeMiddleName;
        this.addresseeLastName = addresseeLastName;
        this.addresseeFullName = addresseeFullName;
        this.additionDate = additionDate;
    }

    public void setAdditionDate() {
        this.additionDate = LocalDate.now();
        this.deletionDate = null;
    }

    public void setAddresseeFirstName(String addresseeFirstName) {
        this.addresseeFirstName = addresseeFirstName;
        setFullName();
    }

    public void setAddresseeMiddleName(String addresseeMiddleName) {
        this.addresseeMiddleName = addresseeMiddleName;
        setFullName();
    }

    public void setAddresseeLastName(String addresseeLastName) {
        this.addresseeLastName = addresseeLastName;
        setFullName();
    }

    private void setFullName() {
        addresseeFullName = addresseeFirstName + " " + addresseeMiddleName + " " + addresseeLastName;
    }

    public BlacklistEntity() {
        // For JPA
    }
}