package ru.teamscore.aggregator;

import java.util.List;
import java.util.Scanner;

public class AggregationService {
    public static void printPaged(List<AggregationRow> rows, int pageSize) {
        Scanner scanner = new Scanner(System.in);
        int total = rows.size();
        int displayed = 0;

        while (displayed < total) {
            int end = Math.min(displayed + pageSize, total);
            System.out.printf("%-32s %-25s %-30s%n", "DEVICE", "DATE", "AVG VALUES");
            for (int i = displayed; i < end; i++) {
                AggregationRow row = rows.get(i);
                System.out.printf("%-32s %-25s %-30s%n",
                        row.deviceName(),
                        row.intervalStart(),
                        arrayToString(row.avgValues()));
            }
            displayed = end;
            System.out.printf("Выведено %d из %d.", displayed, total);
            if (displayed < total) {
                scanner.nextLine();
            }
        }
    }

    private static String arrayToString(double[] values) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            sb.append(String.format("%.3f", values[i]));
            if (i < values.length - 1) sb.append(", ");
        }
        return sb.toString();
    }
}
