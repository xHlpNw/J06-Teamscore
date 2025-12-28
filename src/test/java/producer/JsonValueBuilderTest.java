package producer;

import org.junit.jupiter.api.Test;
import ru.teamscore.common.SensorType;
import ru.teamscore.producer.JsonValueBuilder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonValueBuilderTest {
    @Test
    void lightJsonTest() {
        String json = JsonValueBuilder.build(SensorType.LIGHT);
        Pattern lightPattern = Pattern.compile("\\{ \"light\": (\\d{1,4}) }");
        Matcher matcher = lightPattern.matcher(json);
        assertTrue(matcher.find(), json);
        int value = Integer.parseInt(matcher.group(1));
        assertTrue(value >= 0 && value <= 1023, json);
    }


    @Test
    void barometerJsonTest() {
        String json = JsonValueBuilder.build(SensorType.BAROMETER);
        Pattern barometerPattern = Pattern.compile(
                "\\{ \"air_pressure\": (\\d+(?:\\.\\d+)?) }"
        );
        Matcher matcher = barometerPattern.matcher(json);
        assertTrue(matcher.find(), json);
        double value = Double.parseDouble(matcher.group(1));
        assertTrue(value >= 90000 && value <= 110000, json);
    }

    @Test
    void locationJsonTest() {
        String json = JsonValueBuilder.build(SensorType.LOCATION);
        Pattern locationPattern = Pattern.compile(
                "\\{ \"longitude\": (-?\\d+(?:\\.\\d+)?), \"latitude\": (-?\\d+(?:\\.\\d+)?) }"
        );
        Matcher matcher = locationPattern.matcher(json);
        assertTrue(matcher.find(), json);
        double longitude = Double.parseDouble(matcher.group(1));
        double latitude = Double.parseDouble(matcher.group(2));
        assertTrue(longitude >= -180 && longitude <= 180, json);
        assertTrue(latitude >= -90 && latitude <= 90, json);
    }

    @Test
    void accelerometerTest() {
        String json = JsonValueBuilder.build(SensorType.ACCELEROMETER);
        Pattern accelerometerPattern = Pattern.compile(
                "\\{ \"x\": (\\d+(?:\\.\\d+)?), \"y\": (\\d+(?:\\.\\d+)?), \"z\": (\\d+(?:\\.\\d+)?) }"
        );
        Matcher matcher = accelerometerPattern.matcher(json);
        assertTrue(matcher.find(), json);
        double x = Double.parseDouble(matcher.group(1));
        double y = Double.parseDouble(matcher.group(1));
        double z = Double.parseDouble(matcher.group(1));

        assertTrue(x >= 0 && x <= 25, json);
        assertTrue(y >= 0 && y <= 25, json);
        assertTrue(z >= 0 && z <= 25, json);
    }
}
