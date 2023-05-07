package com.messenger.sharedlib.ddd.domain;

import java.io.Serializable;

public interface Identity<T> extends Serializable {
    T getId();
}
