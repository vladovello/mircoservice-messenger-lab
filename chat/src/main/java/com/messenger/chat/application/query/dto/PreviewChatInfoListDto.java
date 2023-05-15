package com.messenger.chat.application.query.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PreviewChatInfoListDto {
    private int pageNumber;
    private int pageSize;
    private List<PreviewChatInfo> previewChatInfoList;
}
