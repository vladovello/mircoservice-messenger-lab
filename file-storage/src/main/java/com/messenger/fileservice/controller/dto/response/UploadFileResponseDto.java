package com.messenger.fileservice.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFileResponseDto {
    private String fileId;
    private long size;
}
