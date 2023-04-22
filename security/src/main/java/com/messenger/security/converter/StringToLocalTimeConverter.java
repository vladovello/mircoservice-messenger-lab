package com.messenger.security.converter;

import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Component
@ConfigurationPropertiesBinding
public class StringToLocalTimeConverter implements Converter<String, LocalTime> {
    @Override
    public LocalTime convert(@NonNull String source) {
        return LocalTime.parse(source, DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
}
