package com.messenger.chat.domain.message;

import lombok.Getter;
import lombok.NonNull;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
public class Attachment {
    @Id
    @Column(nullable = false)
    @NonNull
    private UUID id;

    @Column(name = "messageId", nullable = false)
    @NonNull
    private UUID messageId;

    @ManyToOne
    @JoinColumn(name = "messageId", insertable = false, updatable = false)
    private Message message;

    @Column(nullable = false)
    @NonNull
    private String storageId;

    @Column(nullable = false)
    @NonNull
    private String fileName;

    @Column(nullable = false) private long fileSize;

    public Attachment(
            @NonNull UUID id,
            @NonNull UUID messageId,
            @NonNull String storageId,
            @NonNull String fileName,
            long fileSize
    ) {
        this.id = id;
        this.messageId = messageId;
        this.storageId = storageId;
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public static @NonNull Attachment createNew(
            @NonNull UUID messageId,
            @NonNull String storageId,
            @NonNull String fileName,
            long fileSize
    ) {
        return new Attachment(generateId(), messageId, storageId, fileName, fileSize);
    }

    protected Attachment() {
        // For JPA
    }

    private static @NonNull UUID generateId() {
        return UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attachment)) return false;

        Attachment that = (Attachment) o;

        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
