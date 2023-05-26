package com.messenger.friendsapp.presentation.controller.dto;

import com.messenger.sharedlib.parameter.dto.DiscreteParamDto;
import com.messenger.sharedlib.parameter.dto.IntervalParamDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class FriendsByParamsDto {
    private DiscreteParamDto<String> fullName;
    private IntervalParamDto<LocalDate> additionDate;
    private DiscreteParamDto<UUID> friendId;
}
