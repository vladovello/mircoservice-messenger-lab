package com.messenger.fileservice.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFileResponseDto {
    private String id;
    private String name;
    private long size;
}
