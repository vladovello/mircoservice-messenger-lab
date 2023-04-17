package com.messenger.authandprofile.application.shared.oneof;

import lombok.NonNull;
import org.jetbrains.annotations.Contract;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class OneOf2<T1, T2> implements OneOf {
    private final T1 value1;
    private final T2 value2;
    private final int index;

    protected OneOf2(int index, T1 value1, T2 value2) {
        this.index = index;
        this.value1 = value1;
        this.value2 = value2;
    }

    @Contract(value = "_ -> new", pure = true)
    public static <T1, T2> @NonNull OneOf2<T1, T2> fromT1(T1 value) {
        return new OneOf2<>(1, value, null);
    }

    @Contract(value = "_ -> new", pure = true)
    public static <T1, T2> @NonNull OneOf2<T1, T2> fromT2(T2 value) {
        return new OneOf2<>(2, null, value);
    }

    @Override
    public Object getValue() {
        if (index == 1) return value1;
        return value2;
    }

    @Override
    public int getIndex() {
        return index;
    }

    public void match(@NonNull Consumer<T1> f1, @NonNull Consumer<T2> f2) {
        Objects.requireNonNull(f1, "f1 is null");
        Objects.requireNonNull(f2, "f2 is null");
        if (index == 1) f1.accept(value1);
        f2.accept(value2);
    }

    public <R> R match(@NonNull Function<T1, R> f1, @NonNull Function<T2, R> f2) {
        Objects.requireNonNull(f1, "f1 is null");
        Objects.requireNonNull(f2, "f2 is null");
        if (index == 1) return f1.apply(value1);
        return f2.apply(value2);
    }
}
