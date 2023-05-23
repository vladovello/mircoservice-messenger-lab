package com.messenger.sharedlib.parameter.order;

public abstract class Direction {
    protected SortingOrder sortingOrder;

    protected Direction(SortingOrder sortingOrder) {
        this.sortingOrder = sortingOrder;
    }

    public SortingOrder getSortingOrder() {
        return sortingOrder;
    }
}
