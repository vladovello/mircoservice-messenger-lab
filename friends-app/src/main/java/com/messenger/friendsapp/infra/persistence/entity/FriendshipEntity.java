package com.messenger.friendsapp.infra.persistence.entity;

import com.messenger.friendsapp.domain.valueobject.FriendshipStatus;
import com.messenger.sharedlib.domain.ddd.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.AfterDomainEventPublication;
import org.springframework.data.domain.DomainEvents;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
        indexes = {@Index(columnList = "requesterId,addresseeId", unique = true)}
)
@Getter
@AllArgsConstructor
public class FriendshipEntity {
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

    @Column(nullable = false)
    @Setter
    private FriendshipStatus friendshipStatus;

    @Column(nullable = false)
    private LocalDate additionDate;

    @Column
    @Setter
    private LocalDate deletionDate;

    @Transient
    @Setter
    private List<DomainEvent> domainEvents;

    public FriendshipEntity(
            UUID id,
            UUID requesterId,
            UUID addresseeId,
            String addresseeFirstName,
            String addresseeMiddleName,
            String addresseeLastName,
            String addresseeFullName,
            FriendshipStatus friendshipStatus,
            LocalDate additionDate
    ) {
        this.id = id;
        this.requesterId = requesterId;
        this.addresseeId = addresseeId;
        this.addresseeFirstName = addresseeFirstName;
        this.addresseeMiddleName = addresseeMiddleName;
        this.addresseeLastName = addresseeLastName;
        this.addresseeFullName = addresseeFullName;
        this.friendshipStatus = friendshipStatus;
        this.additionDate = additionDate;
    }

    @DomainEvents
    public List<DomainEvent> domainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }

    @AfterDomainEventPublication
    public void clearDomainEvents() {
        domainEvents.clear();
    }

    public void addEvent(DomainEvent domainEvent) {
        domainEvents.add(domainEvent);
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

    public FriendshipEntity() {
        // For JPA
    }
}
