package com.messenger.notification.controller;

import com.messenger.notification.dto.ChangeStatusDto;
import com.messenger.notification.dto.NotificationListDto;
import com.messenger.notification.dto.SearchParamsDto;
import com.messenger.notification.dto.UnreadCountDto;
import com.messenger.notification.service.NotificationService;
import com.messenger.notification.service.exception.NotificationDoesNotBelongToUserException;
import com.messenger.security.jwt.PayloadPrincipal;
import com.messenger.sharedlib.presentation.errorhandling.ApiError;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/notifications")
@Validated
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("list")
    public ResponseEntity<NotificationListDto> getNotificationList(
            int pageNumber,
            int pageSize,
            @RequestBody(required = false) @NonNull SearchParamsDto searchParamsDto,
            @NonNull Authentication authentication
    ) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();

        var notificationDtos = notificationService.getNotificationsPaginatedWithParams(
                pageNumber,
                pageSize,
                principal.getId(),
                searchParamsDto.getReceivingDate(),
                searchParamsDto.getNotificationTypes(),
                searchParamsDto.getNotificationText()
        );

        return ResponseEntity.ok(new NotificationListDto(notificationDtos));
    }

    @GetMapping("unread-count")
    public ResponseEntity<UnreadCountDto> getUnreadCount(@NonNull Authentication authentication) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();
        var count = notificationService.getUnreadCount(principal.getId());

        return ResponseEntity.ok(new UnreadCountDto(count));
    }

    @PatchMapping("change-status")
    public ResponseEntity<Object> changeStatus(
            @RequestBody @NonNull ChangeStatusDto changeStatusDto,
            @NonNull Authentication authentication
    ) {
        var principal = (PayloadPrincipal) authentication.getPrincipal();
        try {
            var count = notificationService.changeStatus(
                    principal.getId(),
                    changeStatusDto.getNotificationIds(),
                    changeStatusDto.getNotificationStatus()
            );
            return ResponseEntity.ok(new UnreadCountDto(count));
        } catch (NotificationDoesNotBelongToUserException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiError(
                    HttpStatus.FORBIDDEN,
                    e.getMessage()
            ));
        }
    }
}
