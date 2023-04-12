package com.messenger.authandprofile.application.profile.model.parameter.param;

import com.messenger.authandprofile.application.profile.model.parameter.field.IntervalField;
import com.messenger.authandprofile.application.profile.model.parameter.order.SortingOrder;
import lombok.Setter;

@SuppressWarnings("rawtypes")
@Setter
public class IntervalParam<T extends Comparable> extends IntervalField<T> {
    public IntervalParam(T lowerBound, T upperBound, SortingOrder sortingOrder) {
        super(lowerBound, upperBound, sortingOrder);
    }
}
