package com.messenger.sharedlib.parameter.mapper;

import com.messenger.sharedlib.parameter.dto.DiscreteParamDto;
import com.messenger.sharedlib.parameter.dto.IntervalParamDto;
import com.messenger.sharedlib.parameter.param.DiscreteParam;
import com.messenger.sharedlib.parameter.param.IntervalParam;
import lombok.NonNull;

public class ParamMapper {
    private ParamMapper() {
    }

    public static <T extends Comparable> @NonNull IntervalParam<T> intervalFromDtoOrDefault(IntervalParamDto<T> dto) {
        return dto == null ? IntervalParam.defaultNullValue() : new IntervalParam<>(
                dto.getFrom(),
                dto.getTo(),
                dto.getSortingOrder()
        );
    }

    public static <T> @NonNull DiscreteParam<T> discreteFromDtoOrDefault(DiscreteParamDto<T> dto) {
        return dto == null ? DiscreteParam.defaultNullValue() : new DiscreteParam<>(
                dto.getValue(),
                dto.getSortingOrder()
        );
    }
}
