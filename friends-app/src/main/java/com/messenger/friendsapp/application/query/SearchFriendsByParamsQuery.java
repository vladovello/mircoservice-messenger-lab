package com.messenger.friendsapp.application.query;

import com.messenger.sharedlib.parameter.param.DiscreteParam;
import com.messenger.sharedlib.parameter.param.IntervalParam;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class SearchFriendsByParamsQuery {
    private int pageNumber;
    private int pageSize;
    private UUID userId;
    private DiscreteParam<String> fullName = DiscreteParam.createDefault();
    private IntervalParam<LocalDate> additionDate = IntervalParam.createDefault();
    private DiscreteParam<UUID> friendId = DiscreteParam.createDefault();
}
