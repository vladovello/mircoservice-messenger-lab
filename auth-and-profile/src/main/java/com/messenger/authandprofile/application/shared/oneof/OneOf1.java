package com.messenger.authandprofile.application.shared.oneof;

import lombok.NonNull;
import org.jetbrains.annotations.Contract;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class OneOf1<T1> implements OneOf {
    private final T1 value1;
    private final int index;

    protected OneOf1(int index, T1 value1) {
        this.index = index;
        this.value1 = value1;
    }

    @Contract(value = "_ -> new", pure = true)
    public static <T1> @NonNull OneOf1<T1> fromT1(T1 value) {
        return new OneOf1<>(1, value);
    }

    @Override
    public Object getValue() {
        return value1;
    }

    @Override
    public int getIndex() {
        return index;
    }

    public void match(@NonNull Consumer<T1> f1) {
        Objects.requireNonNull(f1, "f1 is null");
        f1.accept(value1);
    }

    public <R> R match(@NonNull Function<T1, R> f1) {
        Objects.requireNonNull(f1, "f1 is null");
        return f1.apply(value1);
    }

    public <R> OneOf1<R> mapT1(@NonNull Function<T1, R> f1) {
        return OneOf1.fromT1(f1.apply(value1));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OneOf1<?> oneOf1)) return false;

        if (getIndex() != oneOf1.getIndex()) return false;
        return Objects.equals(value1, oneOf1.value1);
    }

    @Override
    public int hashCode() {
        int result = value1 != null ? value1.hashCode() : 0;
        result = 31 * result + getIndex();
        return result;
    }
}
