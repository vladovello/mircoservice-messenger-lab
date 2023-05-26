package com.messenger.fileservice.controller;

import com.messenger.fileservice.server.FileServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("integration/file")
@Slf4j
public class IntegrationFileController {
    private final FileServer fileServer;

    public IntegrationFileController(FileServer fileServer) {
        this.fileServer = fileServer;
    }

    @GetMapping("exists")
    public Boolean isExists(@RequestParam("ids") List<String> ids) {
        return fileServer.isAllExists(ids);
    }
}
