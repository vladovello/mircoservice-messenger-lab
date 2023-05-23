package com.messenger.notification.dto;

import com.messenger.notification.entity.NotificationType;
import com.messenger.sharedlib.parameter.dto.DiscreteParamDto;
import com.messenger.sharedlib.parameter.dto.IntervalParamDto;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class SearchParamsDto {
    private IntervalParamDto<LocalDate> receivingDate;
    private DiscreteParamDto<List<NotificationType>> notificationTypes;
    private DiscreteParamDto<String> notificationText;
}
