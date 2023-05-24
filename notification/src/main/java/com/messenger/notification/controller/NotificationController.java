package com.messenger.notification.controller;

import com.messenger.notification.dto.ChangeStatusDto;
import com.messenger.notification.dto.NotificationListDto;
import com.messenger.notification.dto.SearchParamsDto;
import com.messenger.notification.dto.UnreadCountDto;
import com.messenger.notification.entity.NotificationFactory;
import com.messenger.notification.repository.NotificationRepository;
import com.messenger.notification.service.NotificationService;
import com.messenger.notification.service.exception.NotificationDoesNotBelongToUserException;
import com.messenger.security.jwt.PayloadPrincipal;
import com.messenger.sharedlib.presentation.errorhandling.ApiError;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationRepository notificationRepository;

    public NotificationController(
            NotificationService notificationService,
            NotificationRepository notificationRepository
    ) {
        this.notificationService = notificationService;
        this.notificationRepository = notificationRepository;
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

    @PostMapping("test")
    public ResponseEntity<Void> test() {
        var uuids = List.of(
                UUID.fromString("007509d2-4bb9-4c9a-9b82-856b919e7ef1"),
                UUID.fromString("c81b4c63-5ac6-458a-afe1-109cd186357d"),
                UUID.fromString("550000f2-9731-4dcf-9cb8-201727c958a3")
        );

        for (int i = 0; i < 15; i++) {
            var notif = NotificationFactory.userLoggedNotification(uuids.get(i % 3));
            notificationRepository.save(notif);
        }

        return ResponseEntity.ok().build();
    }
}
