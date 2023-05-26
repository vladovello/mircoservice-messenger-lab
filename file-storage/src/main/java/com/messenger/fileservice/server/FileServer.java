package com.messenger.fileservice.server;

import java.util.List;

public interface FileServer {

    /**
     * @param fileName name of the file
     * @param content  content of the file in bytes
     * @return id of the file in the file storage
     */
    UploadFileResult upload(String fileName, byte[] content);

    /**
     * @param id id of the file in file storage
     * @return content of the file in bytes
     */
    byte[] download(String id);

    boolean isAllExists(List<String> ids);
}
