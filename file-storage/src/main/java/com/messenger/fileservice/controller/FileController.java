package com.messenger.fileservice.controller;

import com.messenger.fileservice.controller.dto.response.UploadFileResponseDto;
import com.messenger.fileservice.server.FileServer;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/file")
@Slf4j
public class FileController {
    private final FileServer fileServer;

    public FileController(FileServer fileServer) {
        this.fileServer = fileServer;
    }

    @PostMapping("upload")
    public ResponseEntity<UploadFileResponseDto> uploadFile(@RequestParam @NonNull MultipartFile file) {
        try {
            log.info("Uploading '{}' file", file.getOriginalFilename());
            return ResponseEntity.ok(new UploadFileResponseDto(
                    fileServer.upload(
                            file.getOriginalFilename(),
                            file.getBytes()
                    ),
                    file.getSize()
            ));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping(value = "download/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> download(@PathVariable String id) {
        return ResponseEntity.ok(fileServer.download(id));
    }
}
