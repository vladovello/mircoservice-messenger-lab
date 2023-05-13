package com.messenger.sharedlib.util;

import lombok.Getter;
import lombok.NonNull;
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
    public static <T> @NonNull Result<T> failure(Exception error) {
        return new Result<>(Failure.create(error));
    }

    public Exception exceptionOrNull() {
        if (isFailure()) return ((Failure) value).getException();
        return null;
    }

    protected void throwOnFailure() throws Exception {
        if (value instanceof Failure) {
            throw exceptionOrNull();
        }
    }

    public T getOrNull() {
        return isSuccess() ? (T) value : null;
    }

    public T getOrThrow() throws Exception {
        throwOnFailure();
        return (T) value;
    }

    public Optional<T> get() {
        return isSuccess() ? Optional.of((T) value) : Optional.empty();
    }

    public <R extends T> T getOrDefault(R defaultValue) {
        if (isFailure()) return defaultValue;
        return (T) value;
    }

    public <R> R fold(Function<T, R> onSuccess, Function<Exception, R> onFailure) {
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

    public Result<T> onFailure(Consumer<Exception> failure) {
        if (isFailure()) failure.accept(exceptionOrNull());
        return this;
    }

    public static <T1, T2> Result<T1> rethrow(@NonNull Result<T2> result) {
        if (result.isSuccess()) {
            return Result.success();
        }

        return Result.failure(result.exceptionOrNull());
    }

    protected static class Failure implements Serializable {
        @Getter private final Exception exception;

        protected Failure(Exception exception) {
            this.exception = exception;
        }

        @Contract(value = "_ -> new", pure = true)
        public static @NonNull Failure create(Exception exception) {
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
