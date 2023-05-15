package com.messenger.chat.application.query;

import com.messenger.chat.application.ChatUserPipeline;
import com.messenger.chat.application.query.dto.ChatInfoDto;
import com.messenger.chat.application.query.handler.ChatInfoQueryHandler;
import com.messenger.sharedlib.util.Result;

public class ChatInfoQueryPipeline extends ChatUserPipeline<ChatInfoQuery, Result<ChatInfoDto>> {

    public ChatInfoQueryPipeline(ChatInfoQueryHandler handler) {
        super(handler);
    }
}
