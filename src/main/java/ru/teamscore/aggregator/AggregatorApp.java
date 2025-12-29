package ru.teamscore.aggregator;

import ru.teamscore.common.SensorType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AggregatorApp {
    private static final int PAGE_SIZE = 16;
    private static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

    public static void main(String[] args) {
        if (args.length < 4) {
            System.out.println("Аргументы командной строки: " +
                    "<sensorType> <from> <to> <interval> [deviceName]");
            System.out.println("Пример: LIGHT 2025-01-01T00:00 2025-01-02T00:00 HOUR device1");
            return;
        }

        SensorType type;
        try {
            type = SensorType.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Некорректный тип датчика: " + args[0]);
            return;
        }

        LocalDateTime from, to;
        try {
            from = LocalDateTime.parse(args[1], DATE_TIME_FORMAT);
            to = LocalDateTime.parse(args[2], DATE_TIME_FORMAT);
        } catch (Exception e) {
            System.out.println("Некорректный формат даты. " +
                    "Используйте yyyy-MM-dd'T'HH:mm");
            return;
        }

        AggregationInterval interval;
        try {
            interval = AggregationInterval.valueOf(args[3].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Некорректный интервал: " + args[3]);
            return;
        }

        String deviceName = args.length >= 5 ? args[4] : null;

        AggregationRepository repository = new AggregationRepository();
        List<AggregationRow> rows = repository.aggregate(type, from, to, interval, deviceName);

        AggregationService.printPaged(rows, PAGE_SIZE);
    }

}
