package com.messenger.authandprofile.application.profile.model.parameter.param;

import com.messenger.authandprofile.application.profile.model.parameter.field.Field;
import com.messenger.authandprofile.application.profile.model.parameter.order.SortingOrder;
import lombok.Setter;

import java.io.Serializable;

@Setter
public class DiscreteParam<T extends Serializable> extends Field<T> {
    public DiscreteParam(T value, SortingOrder filterType) {
        super(value, filterType);
    }
}
