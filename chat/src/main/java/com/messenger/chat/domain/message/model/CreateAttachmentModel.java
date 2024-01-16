package com.messenger.chat.domain.message.model;

import lombok.Value;

@Value
public class CreateAttachmentModel {
    String id;
    String fileName;
    long size;
}
