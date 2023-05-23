package com.messenger.sharedlib.parameter.field;


import com.messenger.sharedlib.parameter.order.Direction;
import com.messenger.sharedlib.parameter.order.SortingOrder;

// INFO: all Field classes may be extended from Field interface marker. This Field class may be replaced by DiscreteField
public abstract class Field<T> extends Direction {
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
