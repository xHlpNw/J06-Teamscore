package producer;

import org.junit.jupiter.api.Test;
import ru.teamscore.common.entity.SensorMessage;
import ru.teamscore.producer.RandomSensorFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RandomSensorFactoryTest {
    @Test
    void notNullMessage() {
        SensorMessage msg = RandomSensorFactory.create();
        assertNotNull(msg);
        assertNotNull(msg.getId());
        assertNotNull(msg.getSensorId());
        assertNotNull(msg.getSensorType());
        assertNotNull(msg.getDeviceName());
        assertNotNull(msg.getMeasuredAt());
        assertNotNull(msg.getSavedAt());
        assertNotNull(msg.getValueJson());
    }

    @Test
    void savedAfterMeasured() {
        SensorMessage msg = RandomSensorFactory.create();
        assertTrue(msg.getSavedAt().isAfter(msg.getMeasuredAt()));
    }

    @Test
    void testDeviceNameLength() {
        SensorMessage msg = RandomSensorFactory.create();
        assertTrue(
                msg.getDeviceName().length() <= 32,
                msg.getDeviceName()
        );
    }

}
