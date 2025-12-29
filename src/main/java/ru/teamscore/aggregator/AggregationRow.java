package ru.teamscore.aggregator;

import java.time.LocalDateTime;

public record AggregationRow(
        String deviceName,
        LocalDateTime intervalStart,
        double[] avgValues) { }
