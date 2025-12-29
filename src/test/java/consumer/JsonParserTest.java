package consumer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import ru.teamscore.common.dto.AccelerometerDTO;
import ru.teamscore.common.dto.BarometerDTO;
import ru.teamscore.common.dto.LightDTO;
import ru.teamscore.common.dto.LocationDTO;
import ru.teamscore.common.entity.LightData;
import ru.teamscore.consumer.JsonParser;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonParserTest {
    @ParameterizedTest
    @ValueSource(ints = { 0, 512, 1023 })
    void lightJsonParse(int value) {
        String json = String.format("{ \"light\": %d }", value);
        LightDTO light = JsonParser.parse(json, LightDTO.class);
        assertNotNull(light);
        assertEquals(light.light(), value);
    }

    @ParameterizedTest
    @ValueSource(doubles = { 90000, 101000, 109999 })
    void barometerJsonParse(double value) {
        String json = String.format(Locale.US, "{ \"air_pressure\": %f }", value);
        BarometerDTO barometer = JsonParser.parse(json, BarometerDTO.class);
        assertNotNull(barometer);
        assertEquals(barometer.airPressure(), value);
    }

    @ParameterizedTest
    @CsvSource({
            "-180, -90",
            "0.0, 0.0",
            "180, 90"
    })
    void locationJsonParse(double longitude, double latitude) {
        String json = String.format(Locale.US,
                "{ \"longitude\": %f, \"latitude\": %f }",
                longitude,
                latitude
        );
        LocationDTO location = JsonParser.parse(json, LocationDTO.class);
        assertNotNull(location);
        assertEquals(longitude, location.longitude());
        assertEquals(latitude, location.latitude());
    }

    @ParameterizedTest
    @CsvSource({
            "0.0001, 25, 0",
            "17.3, 12.4, 2.323435"
    })
    void accelerometerJsonParse(double x, double y, double z) {
        String json = String.format(Locale.US,
                "{ \"x\": %f, \"y\": %f, \"z\": %f }",
                x, y, z);
        AccelerometerDTO accelerometer = JsonParser.parse(
                json, AccelerometerDTO.class);
        assertNotNull(accelerometer);
        assertEquals(x, accelerometer.x());
        assertEquals(y, accelerometer.y());
        assertEquals(z, accelerometer.z());
    }
}
