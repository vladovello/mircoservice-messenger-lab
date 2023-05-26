package com.messenger.fileservice.server;

public interface FileServer {
    String upload(String fileName, byte[] content);
    byte[] download(String id);
}
