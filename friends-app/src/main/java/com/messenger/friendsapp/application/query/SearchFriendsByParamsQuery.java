package com.messenger.friendsapp.application.query;

import com.messenger.sharedlib.parameter.param.DiscreteParam;
import com.messenger.sharedlib.parameter.param.IntervalParam;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class SearchFriendsByParamsQuery {
    private int pageNumber;
    private int pageSize;
    private UUID userId;
    private DiscreteParam<String> fullName;
    private IntervalParam<LocalDate> additionDate;
    private DiscreteParam<UUID> friendId;
}
