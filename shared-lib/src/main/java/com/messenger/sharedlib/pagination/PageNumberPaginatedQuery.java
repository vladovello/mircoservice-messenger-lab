package com.messenger.sharedlib.pagination;

import lombok.Getter;

@Getter
public abstract class PageNumberPaginatedQuery {
    protected int pageNumber;
    protected int pageSize;

    protected PageNumberPaginatedQuery() {
    }

    protected PageNumberPaginatedQuery(int pageNumber, int pageSize) {
        this.setPageNumber(pageNumber);
        this.setPageSize(pageSize);
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = Math.max(pageNumber, 0);
    }

    public void setPageSize(int pageSize) {
        if (pageSize < 1 || pageSize > getMaxPageSize()) {
            this.pageSize = 10;
        } else {
            this.pageSize = pageSize;
        }
    }

    public abstract int getMaxPageSize();
}
