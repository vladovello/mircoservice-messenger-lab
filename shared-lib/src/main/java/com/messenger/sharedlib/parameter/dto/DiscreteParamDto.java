package com.messenger.sharedlib.parameter.dto;

import com.messenger.sharedlib.parameter.order.SortingOrder;
import lombok.Data;

@Data
public class DiscreteParamDto<T> {
    T value;
    SortingOrder sortingOrder;
}
