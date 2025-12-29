package aggregator;

import org.junit.jupiter.api.Test;
import ru.teamscore.aggregator.AggregationInterval;
import ru.teamscore.aggregator.IntervalTruncator;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntervalTruncatorTest {
    LocalDateTime dt = LocalDateTime.of(
            2025,
            12,
            28,
            12,
            35,
            59,
            789
    );

    @Test
    void minuteIntervalTruncate() {
        LocalDateTime expectedDt = LocalDateTime.of(
                2025,
                12,
                28,
                12,
                35
        );
        assertEquals(expectedDt, IntervalTruncator.truncate(dt, AggregationInterval.MINUTE));
    }

    @Test
    void hourIntervalTruncate() {
        LocalDateTime expectedDt = LocalDateTime.of(
                2025,
                12,
                28,
                12,
                0
        );
        assertEquals(expectedDt, IntervalTruncator.truncate(dt, AggregationInterval.HOUR));
    }

    @Test
    void dayIntervalTruncate() {
        LocalDateTime expectedDt = LocalDateTime.of(
                2025,
                12,
                28,
                0,
                0
        );
        assertEquals(expectedDt, IntervalTruncator.truncate(dt, AggregationInterval.DAY));
    }

    @Test
    void weekIntervalTruncate() {
        LocalDateTime expectedDt = LocalDateTime.of(
                2025,
                12,
                22,
                0,
                0
        );
        assertEquals(expectedDt, IntervalTruncator.truncate(dt, AggregationInterval.WEEK));
    }
}
