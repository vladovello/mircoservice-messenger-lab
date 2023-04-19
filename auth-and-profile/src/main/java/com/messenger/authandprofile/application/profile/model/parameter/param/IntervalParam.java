package com.messenger.authandprofile.application.profile.model.parameter.param;

import com.messenger.authandprofile.application.profile.model.parameter.field.IntervalField;
import com.messenger.authandprofile.application.profile.model.parameter.order.SortingOrder;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("rawtypes")
@Setter
public class IntervalParam<T extends Comparable> extends IntervalField<T> {
    public IntervalParam(T lowerBound, T upperBound, SortingOrder sortingOrder) {
        super(lowerBound, upperBound, sortingOrder);
    }

    protected IntervalParam() {
        super();
    }

    @Contract(" -> new")
    public static <T extends Comparable> @NotNull IntervalParam<T> createDefault() {
        return new IntervalParam<>();
    }
}
