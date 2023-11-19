package com.messenger.authandprofile.application.profile.model.parameter.field;

import com.google.common.collect.Range;
import com.messenger.authandprofile.application.profile.exception.EmptyIntervalException;
import com.messenger.authandprofile.application.profile.exception.IntervalRangeViolationException;
import com.messenger.authandprofile.application.profile.model.parameter.order.Direction;
import com.messenger.authandprofile.application.profile.model.parameter.order.SortingOrder;
import lombok.Getter;

// TODO: 20.04.2023 move Direction to Param classes
@SuppressWarnings("rawtypes")
public abstract class IntervalField<T extends Comparable> extends Direction {
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
            if (lowerBound.compareTo(upperBound) > 0) {
                throw new IntervalRangeViolationException(lowerBound.toString(), upperBound.toString());
            }

            this.interval = Range.closed(lowerBound, upperBound);
        }
    }

    protected IntervalField() {
        super(SortingOrder.ASC);
        this.interval = null;
    }
}
