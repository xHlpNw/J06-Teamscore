package ru.teamscore.aggregator;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class IntervalTruncator {
    public static LocalDateTime truncate(
            LocalDateTime dt,
            AggregationInterval interval) {
        return switch (interval) {
            case MINUTE -> dt.withSecond(0).withNano(0);
            case HOUR -> dt.withMinute(0).withSecond(0).withNano(0);
            case DAY -> dt.toLocalDate().atStartOfDay();
            case WEEK -> dt.toLocalDate()
                    .with(DayOfWeek.MONDAY)
                    .atStartOfDay();
        };
    }
}
