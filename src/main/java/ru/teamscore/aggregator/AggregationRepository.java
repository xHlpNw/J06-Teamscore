package ru.teamscore.aggregator;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import ru.teamscore.common.HibernateUtil;
import ru.teamscore.common.SensorType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class AggregationRepository {
    public List<AggregationRow> aggregate(
            SensorType type,
            LocalDateTime from,
            LocalDateTime to,
            AggregationInterval interval,
            String deviceName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            String trunc = switch (interval) {
                case MINUTE -> "minute";
                case HOUR -> "hour";
                case DAY -> "day";
                case WEEK -> "week";
            };

            String sql = getSql(type, trunc, deviceName);

            NativeQuery<Object[]> query = session.createNativeQuery(sql, Object[].class)
                    .setParameter("from", from)
                    .setParameter("to", to);

            if (deviceName != null) {
                query.setParameter("device", deviceName);
            }

            List<Object[]> raw = query.getResultList();
            return raw.stream()
                    .map(this::mapToAggregationRow)
                    .toList();
        }
    }

    private String getSql(SensorType type, String trunc, String deviceName) {
        String table = switch (type) {
            case LIGHT -> "light_data";
            case BAROMETER -> "barometer_data";
            case LOCATION -> "location_data";
            case ACCELEROMETER -> "accelerometer_data";
        };

        String values = switch (type) {
            case LIGHT -> "ARRAY[AVG(light)]";
            case BAROMETER -> "ARRAY[AVG(air_pressure)]";
            case LOCATION -> "ARRAY[AVG(longitude), AVG(latitude)]";
            case ACCELEROMETER -> "ARRAY[AVG(x), AVG(y), AVG(z)]";
        };

        return """
                SELECT
                    device_name,
                    date_trunc('%s', measured_at) AS span,
                    %s AS values
                FROM %s
                WHERE measured_at BETWEEN :from AND :to
                %s
                GROUP BY device_name, span
                ORDER BY device_name ASC, span DESC
            """.formatted(
                trunc,
                values,
                table,
                deviceName != null ? "AND device_name = :device" : ""
        );
    }

    private AggregationRow mapToAggregationRow(Object[] row) {
        String deviceName = (String) row[0];

        LocalDateTime intervalStart = ((java.sql.Timestamp) row[1]).toLocalDateTime();


        try {
            BigDecimal[] bdArray = (BigDecimal[]) row[2];
            double[] avgValues = new double[bdArray.length];
            for (int i = 0; i < bdArray.length; i++) {
                avgValues[i] = bdArray[i].doubleValue();
            }
            return new AggregationRow(deviceName, intervalStart, avgValues);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
