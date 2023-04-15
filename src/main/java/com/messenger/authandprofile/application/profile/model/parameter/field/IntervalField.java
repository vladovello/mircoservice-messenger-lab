package com.messenger.authandprofile.application.profile.model.parameter.field;

import com.google.common.collect.Range;
import com.messenger.authandprofile.application.profile.model.parameter.exception.EmptyIntervalException;
import com.messenger.authandprofile.application.profile.model.parameter.order.Order;
import com.messenger.authandprofile.application.profile.model.parameter.order.SortingOrder;
import lombok.Getter;

@SuppressWarnings("rawtypes")
public abstract class IntervalField<T extends Comparable> extends Order {
    @Getter protected final Range<T> interval;

    protected IntervalField(T lowerBound, T upperBound, SortingOrder sortingOrder) {
        super(sortingOrder);

        if (lowerBound == null && upperBound == null) {
            throw new EmptyIntervalException();
        } else if (lowerBound == null) {
            this.interval = Range.atMost(upperBound);
        } else if (upperBound == null) {
            this.interval = Range.atLeast(lowerBound);
        } else {
            this.interval = Range.closed(lowerBound, upperBound);
        }
    }
}
