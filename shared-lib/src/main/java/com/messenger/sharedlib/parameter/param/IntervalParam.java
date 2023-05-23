package com.messenger.sharedlib.parameter.param;

import com.messenger.sharedlib.parameter.dto.IntervalParamDto;
import com.messenger.sharedlib.parameter.field.IntervalField;
import com.messenger.sharedlib.parameter.order.SortingOrder;
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

    public static <T extends Comparable> @NotNull IntervalParam<T> fromDto(IntervalParamDto<T> dto) {
        return dto == null ? IntervalParam.createDefault() : new IntervalParam<>(
                dto.getFrom(),
                dto.getTo(),
                dto.getSortingOrder()
        );
    }
}
