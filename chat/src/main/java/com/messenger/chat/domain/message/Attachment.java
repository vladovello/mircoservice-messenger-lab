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
    private UUID storageId;

    @Column(nullable = false)
    @NonNull
    private String fileName;

    @Column(nullable = false) private long fileSize;

    public Attachment(
            @NonNull UUID id,
            @NonNull UUID messageId,
            @NonNull UUID storageId,
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
            @NonNull UUID id,
            @NonNull UUID messageId,
            @NonNull UUID storageId,
            @NonNull String fileName,
            long fileSize
    ) {
        return new Attachment(id, messageId, storageId, fileName, fileSize);
    }

    protected Attachment() {
        // For JPA
    }
}
