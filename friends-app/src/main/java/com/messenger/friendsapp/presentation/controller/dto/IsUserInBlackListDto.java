package com.messenger.friendsapp.presentation.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IsUserInBlackListDto {
    Boolean isBlacklisted;
}
