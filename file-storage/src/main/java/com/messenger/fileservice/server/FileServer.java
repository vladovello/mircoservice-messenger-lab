package com.messenger.fileservice.server;

public interface FileServer {
    String upload(byte[] content);
    byte[] download(String id);
}
