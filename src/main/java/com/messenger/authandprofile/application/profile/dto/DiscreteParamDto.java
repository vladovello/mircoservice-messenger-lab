package com.messenger.authandprofile.application.profile.dto;

import com.messenger.authandprofile.application.profile.model.parameter.order.SortingOrder;
import lombok.Data;

@Data
public class DiscreteParamDto<T> {
    T value;
    SortingOrder sortingOrder;
}
