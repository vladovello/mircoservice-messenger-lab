package com.messenger.fileservice.server.impl;

import com.messenger.fileservice.server.FileServer;
import com.messenger.fileservice.server.UploadFileResult;
import com.messenger.fileservice.server.config.MinioConfig;
import com.messenger.fileservice.server.exception.FileServerException;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.StatObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.MinioException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.security.InvalidKeyException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class MinioFileServer implements FileServer {
    private static final String FILE_NAME_REGEX = "[^a-zA-Z0-9.\\-]";
    private static final String REPLACEMENT = "_";

    private final MinioClient minioClient;
    private final MinioConfig minioConfig;

    public MinioFileServer(MinioClient minioClient, MinioConfig minioConfig) {
        this.minioClient = minioClient;
        this.minioConfig = minioConfig;
    }

    @Override
    public UploadFileResult upload(@NonNull String fileName, byte[] content) {
        try {
            var id = UUID.randomUUID().toString();
            var formattedFileName = fileName.replaceAll(FILE_NAME_REGEX, REPLACEMENT);
            var fileId = id + ":" + formattedFileName;

            minioClient.putObject(
                    PutObjectArgs
                            .builder()
                            .bucket(minioConfig.getBucket())
                            .object(fileId)
                            .stream(new ByteArrayInputStream(content), content.length, -1)
                            .build()
            );

            return new UploadFileResult(fileId, formattedFileName);
        } catch (MinioException e) {
            log.error("Something went wrong with Minio. {}", e.getMessage());
            throw new FileServerException(e);
        } catch (InvalidKeyException e) {
            log.error("Somehow keys for Minio is invalid. {}", e.getMessage());
            throw new FileServerException(e);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new FileServerException(e);
        }
    }

    @Override
    public byte[] download(String fileId) {
        var objectArgs = GetObjectArgs
                .builder()
                .bucket(minioConfig.getBucket())
                .object(fileId)
                .build();

        try (var in = minioClient.getObject(objectArgs)) {
            return in.readAllBytes();
        } catch (MinioException e) {
            log.error("Something went wrong with Minio. {}", e.getMessage());
            throw new FileServerException(e);
        } catch (InvalidKeyException e) {
            log.error("Somehow keys for Minio is invalid. {}", e.getMessage());
            throw new FileServerException(e);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new FileServerException(e);
        }
    }

    @Override
    public boolean isAllExists(List<String> ids) {
        try {
            for (var id : ids) {
                minioClient.statObject(StatObjectArgs
                        .builder()
                        .bucket(minioConfig.getBucket())
                        .object(id)
                        .build()
                );
            }
            return true;
        } catch (ErrorResponseException e) {
            log.error("Object probably doesn't exists {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new FileServerException(e);
        }
    }
}
