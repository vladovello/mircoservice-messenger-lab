package com.messenger.chat.application.query.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FileSizeMetric {
    BYTES("bytes", 1L),
    KB("KiB", 1_000L),
    MB("MiB", 1_000_000L),
    GB("GiB", 1_000_000_000L),
    TB("TiB", 1_000_000_000_000L),
    PB("PiB", 1_000_000_000_000_000L),
    EB("EiB", 1_000_000_000_000_000_000L);

    @Getter private final String metric;
    @Getter private final long size;

    @Getter private static final List<Long> SIZES = new ArrayList<>();
    private static final Map<Long, FileSizeMetric> BY_SIZES = new HashMap<>();

    static {
        for (FileSizeMetric metric : values()) {
            SIZES.add(metric.getSize());
            BY_SIZES.put(metric.getSize(), metric);
        }
    }

    public static FileSizeMetric valueOfLabel(Long size) {
        return BY_SIZES.get(size);
    }

    FileSizeMetric(String metric, long size) {
        this.metric = metric;
        this.size = size;
    }
}
