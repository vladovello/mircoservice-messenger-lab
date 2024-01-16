package com.messenger.sharedlib.domain.ddd;

import java.io.Serializable;

public interface Identity<T> extends Serializable {
    T getId();
}
