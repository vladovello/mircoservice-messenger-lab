package com.messenger.authandprofile.presentation;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CQSLoggerProbe {
    private CQSLoggerProbe() {
    }

    public static void execStarted(@NonNull Class<?> clazz) {
        log.info("STARTED: " + clazz.getName());
    }

    public static void execFinished(@NonNull Class<?> clazz) {
        log.info("FINISHED: " + clazz.getName());
    }
}
