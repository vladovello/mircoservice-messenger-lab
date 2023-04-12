package com.messenger.authandprofile.application.profile.model.parameter.field;

import com.messenger.authandprofile.application.profile.model.parameter.order.Order;
import com.messenger.authandprofile.application.profile.model.parameter.order.SortingOrder;

import java.io.Serializable;

public abstract class Field<T extends Serializable> extends Order {
    protected T value;

    protected Field(T value, SortingOrder sortingOrder) {
        super(sortingOrder);
        setValue(value);
    }

    public T getValue() {
        return value;
    }

    private void setValue(T value) {
        this.value = value;
    }
}
