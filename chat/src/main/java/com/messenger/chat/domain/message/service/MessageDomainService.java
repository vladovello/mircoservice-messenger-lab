package com.messenger.chat.domain.message.service;

import com.messenger.chat.domain.chatparticipant.ChatParticipant;
import com.messenger.chat.domain.message.Message;
import com.messenger.chat.domain.message.model.CreateAttachmentModel;
import com.messenger.sharedlib.util.Result;
import com.messenger.sharedlib.util.Unit;

import java.util.List;

public interface MessageDomainService {
    /**
     * @param message message to save
     * @return {@code Result<Unit>} describing whether the result of the function execution was successful
     */
    Result<Unit> saveMessage(Message message);
    Result<Unit> assignAttachments(Message message, List<CreateAttachmentModel> attachmentModels);

    Result<Unit> changeMessage(ChatParticipant actionPerformer, Message message);

    Result<Unit> deleteMessage(ChatParticipant actionPerformer, Message message);
}
