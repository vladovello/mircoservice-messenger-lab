package com.messenger.friendsapp.application.query;

import com.messenger.sharedlib.parameter.dto.DiscreteParamDto;
import com.messenger.sharedlib.parameter.dto.IntervalParamDto;
import com.messenger.sharedlib.parameter.mapper.ParamMapper;
import com.messenger.sharedlib.parameter.param.DiscreteParam;
import com.messenger.sharedlib.parameter.param.IntervalParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class SearchFriendsByParamsQuery {
    private int pageNumber;
    private int pageSize;
    private UUID userId;
    private @NonNull DiscreteParam<String> fullName;
    private @NonNull IntervalParam<LocalDate> additionDate;
    private @NonNull DiscreteParam<UUID> friendId;

    public SearchFriendsByParamsQuery(
            int pageNumber,
            int pageSize,
            UUID userId,
            DiscreteParamDto<String> fullName,
            IntervalParamDto<LocalDate> additionDate,
            DiscreteParamDto<UUID> friendId
    ) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.userId = userId;
        this.fullName = ParamMapper.discreteFromDtoOrDefault(fullName);
        this.additionDate = ParamMapper.intervalFromDtoOrDefault(additionDate);
        this.friendId = ParamMapper.discreteFromDtoOrDefault(friendId);
    }
}
