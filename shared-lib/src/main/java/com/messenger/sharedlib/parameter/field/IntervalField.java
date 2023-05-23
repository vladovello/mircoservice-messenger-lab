package com.messenger.sharedlib.parameter.field;

import com.google.common.collect.Range;
import com.messenger.sharedlib.parameter.exception.EmptyIntervalException;
import com.messenger.sharedlib.parameter.exception.IntervalRangeViolationException;
import com.messenger.sharedlib.parameter.order.Direction;
import com.messenger.sharedlib.parameter.order.SortingOrder;
import lombok.Getter;

// TODO: 20.04.2023 move Direction to Param classes
//  Param class may contain Direction and Field classes. IntervalField and DiscreteField may extend Field abstract class
//  while Param classes may extend Param abstract class. This will make the classes more extensible.
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
