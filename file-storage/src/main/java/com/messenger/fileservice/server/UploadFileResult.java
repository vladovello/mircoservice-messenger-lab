package com.messenger.fileservice.server;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFileResult {
    private String fileId;
    private String fileName;
}
