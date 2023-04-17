package com.messenger.authandprofile.application.shared.oneof;

import io.vavr.API;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;

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
}
