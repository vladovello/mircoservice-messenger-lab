package com.messenger.fileservice.server.impl;

import com.messenger.fileservice.server.FileServer;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MinioFileServer implements FileServer {
    private final MinioClient minioClient;

    public MinioFileServer(MinioClient minioClient) {
        this.minioClient = minioClient;
    }

    @Override
    public String upload(byte[] content) {
//        minioClient
        return null;
    }

    @Override
    public byte[] download(String id) {
        return new byte[0];
    }
}
