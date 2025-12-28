package ru.teamscore.producer;

import ru.teamscore.common.SensorType;
import ru.teamscore.common.entity.SensorMessage;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

public class RandomSensorFactory {
    private static final Random rand = new Random();

    private static final String[] devices = {
        "Smart Cleaner",
        "Earphones",
        "Smartphone",
        "Smart Watch"
    };

    public static SensorMessage create() {
        SensorType type = SensorType.values()[rand.nextInt(
                SensorType.values().length
        )];

        SensorMessage msg = new SensorMessage(
                UUID.randomUUID(),
                UUID.randomUUID(),
                type,
                devices[rand.nextInt(devices.length)],
                LocalDateTime.now().minusSeconds(5),
                LocalDateTime.now(),
                JsonValueBuilder.build(type)
        );
        return msg;
    }
}
