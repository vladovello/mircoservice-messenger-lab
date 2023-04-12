package com.messenger.authandprofile.application.profile.model.parameter.order;

import java.io.Serializable;

public abstract class Order implements Serializable {
    protected SortingOrder sortingOrder;

    protected Order(SortingOrder sortingOrder) {
        this.sortingOrder = sortingOrder;
    }

    public SortingOrder getSortingOrder() {
        return sortingOrder;
    }
}
