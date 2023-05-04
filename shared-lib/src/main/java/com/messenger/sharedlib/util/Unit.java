package com.messenger.sharedlib.util;

import lombok.NonNull;

public class Unit implements Comparable<Unit> {
    public static final Unit INSTANCE = new Unit();

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Unit;
    }

    @Override
    public int compareTo(@NonNull Unit o) {
        return 0;
    }

    @Override
    public String toString() {
        return "()";
    }
}
