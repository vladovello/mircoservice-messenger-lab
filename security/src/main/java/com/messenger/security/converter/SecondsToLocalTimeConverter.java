package com.messenger.security.converter;

import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@ConfigurationPropertiesBinding
public class SecondsToLocalTimeConverter implements Converter<Integer, LocalTime> {
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MINUTES_IN_HOUR = 60;

    @Override
    public LocalTime convert(@NonNull Integer seconds) {
        var hours = getHours(seconds);
        seconds -= hours * MINUTES_IN_HOUR * SECONDS_IN_MINUTE;
        var minutes = getMinutes(seconds);
        seconds -= minutes * SECONDS_IN_MINUTE;

        return LocalTime.of(hours, minutes, seconds);
    }

    private int getHours(int seconds) {
        return seconds / MINUTES_IN_HOUR / SECONDS_IN_MINUTE;
    }

    private int getMinutes(int seconds) {
        return seconds / SECONDS_IN_MINUTE;
    }
}