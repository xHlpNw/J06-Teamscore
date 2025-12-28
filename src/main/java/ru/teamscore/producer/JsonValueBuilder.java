package ru.teamscore.producer;

import ru.teamscore.common.SensorType;

import java.util.Locale;
import java.util.Random;

public class JsonValueBuilder {
    private static final Random rand = new Random();

    public static String build(SensorType sensorType) {
        return switch (sensorType) {
            case LIGHT ->
                    String.format(
                            "{ \"light\": %d }",
                            rand.nextInt(1024)
                    );
            case BAROMETER ->
                    String.format(Locale.US,
                            "{ \"air_pressure\": %f }",
                            90000 + rand.nextDouble() * 20000
                    );
            case LOCATION ->
                    String.format(Locale.US,
                            "{ \"longitude\": %f, \"latitude\": %f }",
                            -180 + rand.nextDouble() * 360,
                            -90 + rand.nextDouble() * 180
                    );
            case ACCELEROMETER ->
                    String.format(Locale.US,
                            "{ \"x\": %f, \"y\": %f, \"z\": %f }",
                            rand.nextDouble() * 25,
                            rand.nextDouble() * 25,
                            rand.nextDouble() * 25
                    );
        };
    }
}
