package com.messenger.sharedlib.util;

import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.jetbrains.annotations.Contract;

import java.io.Serializable;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class Result<T> {
    private final Object value;

    public boolean isSuccess() {
        return !(value instanceof Failure);
    }

    public boolean isFailure() {
        return value instanceof Failure;
    }

    protected Result() {
        this.value = Unit.INSTANCE;
    }

    protected Result(T value) {
        this.value = value;
    }

    protected Result(Failure failure) {
        this.value = failure;
    }

    @Contract(value = " -> new", pure = true)
    public static <T> @NonNull Result<T> success() {
        return new Result<>();
    }

    @Contract(value = "_ -> new", pure = true)
    public static <T> @NonNull Result<T> success(T value) {
        return new Result<>(value);
    }

    @Contract(value = "_ -> new", pure = true)
    public static <T> @NonNull Result<T> failure(Throwable error) {
        return new Result<>(Failure.create(error));
    }

    protected Throwable exceptionOrNull() {
        if (isFailure()) return ((Failure) value).getException();
        return null;
    }

    protected void throwOnFailure() throws Throwable {
        if (value instanceof Failure) {
            throw exceptionOrNull();
        }
    }

    public T getOrThrow() throws Throwable {
        throwOnFailure();
        return (T) value;
    }

    public Optional<T> get() {
        if (isSuccess()) {
            return Optional.of((T) value);
        }

        return Optional.empty();
    }

    public <R extends T> T getOrDefault(R defaultValue) {
        if (isFailure()) return defaultValue;
        return (T) value;
    }

    public <R> R fold(Function<T, R> onSuccess, Function<Throwable, R> onFailure) {
        if (isSuccess()) return onSuccess.apply((T) value);
        return onFailure.apply(((Failure) value).getException());
    }

    public <R> Result<R> map(Function<T, R> transform) {
        if (isSuccess()) return Result.success(transform.apply((T) value));
        return new Result<>((R) value);
    }

    public Result<T> onSuccess(Consumer<T> success) {
        if (isSuccess()) success.accept((T) value);
        return this;
    }

    public Result<T> onFailure(Consumer<Throwable> failure) {
        if (isFailure()) failure.accept(exceptionOrNull());
        return this;
    }

    protected static class Failure implements Serializable {
        @Getter private final Throwable exception;

        protected Failure(Throwable exception) {
            this.exception = exception;
        }

        @Contract(value = "_ -> new", pure = true)
        public static @NonNull Failure create(Throwable exception) {
            return new Failure(exception);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Failure)) return false;

            Failure failure = (Failure) o;

            return exception.equals(failure.exception);
        }

        @Override
        public int hashCode() {
            return exception.hashCode();
        }

        @Override
        public String toString() {
            return "Failure(" + exception + ')';
        }
    }
}
