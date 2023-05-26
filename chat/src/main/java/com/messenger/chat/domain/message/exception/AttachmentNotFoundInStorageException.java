package com.messenger.chat.domain.message.exception;

import com.messenger.sharedlib.exception.NotFoundException;

public class AttachmentNotFoundInStorageException extends NotFoundException {
    public AttachmentNotFoundInStorageException() {
        super("Uploaded attachment not found");
    }
}
