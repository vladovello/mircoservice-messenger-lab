package com.messenger.authandprofile.application.profile.model.parameter.field;

import com.messenger.authandprofile.application.profile.model.parameter.order.Direction;
import com.messenger.authandprofile.application.profile.model.parameter.order.SortingOrder;

import java.io.Serializable;

// INFO: all Field classes may be extended from Field interface marker. This Field class may be replaced by DiscreteField
public abstract class Field<T extends Serializable> extends Direction {
    protected T value;

    protected Field(T value, SortingOrder sortingOrder) {
        super(sortingOrder);
        setValue(value);
    }

    protected Field() {
        super(SortingOrder.ASC);
        this.value = null;
    }

    public T getValue() {
        return value;
    }

    private void setValue(T value) {
        this.value = value;
    }
}
