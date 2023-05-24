package com.messenger.sharedlib.parameter.mapper;

import com.messenger.sharedlib.parameter.dto.DiscreteParamDto;
import com.messenger.sharedlib.parameter.dto.IntervalParamDto;
import com.messenger.sharedlib.parameter.param.DiscreteParam;
import com.messenger.sharedlib.parameter.param.IntervalParam;
import lombok.NonNull;

public class ParamMapper {
    private ParamMapper() {
    }

    public static <T extends Comparable> @NonNull IntervalParam<T> intervalFromDto(IntervalParamDto<T> dto) {
        return dto == null ? IntervalParam.createDefault() : new IntervalParam<>(
                dto.getFrom(),
                dto.getTo(),
                dto.getSortingOrder()
        );
    }

    public static <T> @NonNull DiscreteParam<T> discreteFromDto(DiscreteParamDto<T> dto) {
        return dto == null ? DiscreteParam.createDefault() : new DiscreteParam<>(
                dto.getValue(),
                dto.getSortingOrder()
        );
    }
}
