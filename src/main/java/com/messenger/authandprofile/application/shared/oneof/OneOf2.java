package com.messenger.authandprofile.application.shared.oneof;

public class OneOf2<T1, T2> {
    private T1 value1;
    private T2 value2;
    private int index = -1;

    private OneOf2(int index, T1 value1, T2 value2) {
        this.index = index;
        this.value1 = value1;
        this.value2 = value2;
    }

    public static <T1, T2> OneOf2<T1, T2> fromT1(T1 value) {
        return new OneOf2<>(1, value, null);
    }

    public static <T1, T2> OneOf2<T1, T2> fromT2(T2 value) {
        return new OneOf2<>(2, null, value);
    }

    public static void main(String[] args) {
//        OneOf2.fromT1()
    }
}
