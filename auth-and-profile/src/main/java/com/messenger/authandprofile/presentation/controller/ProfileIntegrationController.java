package com.messenger.authandprofile.presentation.controller;

import an.awesome.pipelinr.Pipeline;
import com.messenger.authandprofile.application.profile.dto.FullNameDto;
import com.messenger.authandprofile.application.profile.query.GetFullNameByIdQuery;
import lombok.NonNull;
import org.springdoc.core.annotations.RouterOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("integration/users/profile")
@RouterOperation
public class ProfileIntegrationController {
    private final Pipeline pipeline;

    public ProfileIntegrationController(Pipeline pipeline) {
        this.pipeline = pipeline;
    }

    @GetMapping("full_name")
    public ResponseEntity<FullNameDto> getFullNameById(@RequestParam @NonNull UUID userId) {
        var optionalFullNameDto = new GetFullNameByIdQuery(userId).execute(pipeline);
        return optionalFullNameDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
