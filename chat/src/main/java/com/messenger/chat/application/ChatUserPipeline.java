package com.messenger.chat.application;

import java.util.ArrayList;
import java.util.List;

public class ChatUserPipeline<I extends ChatUser, O> extends CommonPipeline<I, O> {
    public ChatUserPipeline(Handler<I, O> handler) {
        with(handler);
        with(new ArrayList<>(List.of()));
    }
}
