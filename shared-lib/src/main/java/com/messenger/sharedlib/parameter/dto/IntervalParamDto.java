package com.messenger.sharedlib.parameter.dto;

import com.messenger.sharedlib.parameter.order.SortingOrder;
import lombok.Data;

@Data
public class IntervalParamDto<T> {
    private T from;
    private T to;
    private SortingOrder sortingOrder;
}
