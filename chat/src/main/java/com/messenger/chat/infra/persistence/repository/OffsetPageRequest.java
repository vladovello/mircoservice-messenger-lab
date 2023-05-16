package com.messenger.chat.infra.persistence.repository;


import lombok.NonNull;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.Optional;

public class OffsetPageRequest implements Pageable, Serializable {
    private static final long serialVersionUID = -25822477129613575L;

    private final int limit;
    private final long offset;
    private final Sort sort;

    public OffsetPageRequest(long offset, int limit, Sort sort) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset index must not be less than zero!");
        }

        if (limit < 1) {
            throw new IllegalArgumentException("Limit must not be less than one!");
        }

        this.limit = limit;
        this.offset = offset;
        this.sort = sort;
    }

    public OffsetPageRequest(long offset, int limit, Sort.Direction direction, String... properties) {
        this(offset, limit, Sort.by(direction, properties));
    }

    public OffsetPageRequest(long offset, int limit) {
        this(offset, limit, Sort.unsorted());
    }

    @Override
    public boolean isPaged() {
        return Pageable.super.isPaged();
    }

    @Override
    public boolean isUnpaged() {
        return Pageable.super.isUnpaged();
    }

    @Override
    public int getPageNumber() {
        return (int) (offset / limit);
    }

    @Override
    public int getPageSize() {
        return limit;
    }

    @Override
    public long getOffset() {
        return offset;
    }

    @Override
    public @NonNull Sort getSort() {
        return sort;
    }

    @Override
    public @NonNull Sort getSortOr(@NonNull Sort sort) {
        return Pageable.super.getSortOr(sort);
    }

    @Override
    public @NonNull Pageable next() {
        return new OffsetPageRequest(getOffset() + getPageSize(), getPageSize(), getSort());
    }

    public OffsetPageRequest previous() {
        return hasPrevious() ? new OffsetPageRequest(getOffset() - getPageSize(), getPageSize(), getSort()) : this;
    }


    @Override
    public @NonNull Pageable previousOrFirst() {
        return hasPrevious() ? previous() : first();
    }

    @Override
    public @NonNull Pageable first() {
        return new OffsetPageRequest(0, getPageSize(), getSort());
    }

    @Override
    public @NonNull Pageable withPage(int pageNumber) {
        return new OffsetPageRequest((long) getPageSize() * pageNumber, getPageSize(), getSort());
    }

    @Override
    public boolean hasPrevious() {
        return offset > limit;
    }

    @Override
    public @NonNull Optional<Pageable> toOptional() {
        return Pageable.super.toOptional();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OffsetPageRequest)) return false;

        OffsetPageRequest that = (OffsetPageRequest) o;

        if (limit != that.limit) return false;
        if (getOffset() != that.getOffset()) return false;
        return getSort().equals(that.getSort());
    }

    @Override
    public int hashCode() {
        int result = limit;
        result = 31 * result + (int) (getOffset() ^ (getOffset() >>> 32));
        result = 31 * result + getSort().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "OffsetPageRequest{" +
                "limit=" + limit +
                ", offset=" + offset +
                ", sort=" + sort +
                '}';
    }
}
