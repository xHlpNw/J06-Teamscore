package ru.teamscore.consumer;

import org.hibernate.Session;
import ru.teamscore.common.SensorType;
import ru.teamscore.common.dto.AccelerometerDTO;
import ru.teamscore.common.dto.BarometerDTO;
import ru.teamscore.common.dto.LightDTO;
import ru.teamscore.common.dto.LocationDTO;
import ru.teamscore.common.entity.*;

public class SensorDataPersister {
    public static void persist(Session session, SensorMessage msg) {
        SensorType type = msg.getSensorType();

        switch (type) {
            case LIGHT -> persistLight(session, msg);
            case BAROMETER -> persistBarometer(session, msg);
            case LOCATION -> persistLocation(session, msg);
            case ACCELEROMETER -> persistAccelerometer(session, msg);
        }
    }

    private static void persistLight(Session session, SensorMessage msg) {
        LightDTO dto = JsonParser.parse(msg.getValueJson(), LightDTO.class);

        LightData data = new LightData();
        data.setLight(dto.light());
        data.setSensorId(msg.getSensorId());
        data.setId(msg.getId());
        data.setDeviceName(msg.getDeviceName());
        data.setMeasuredAt(msg.getMeasuredAt());

        session.persist(data);
    }

    private static void persistBarometer(Session session, SensorMessage msg) {
        BarometerDTO dto = JsonParser.parse(msg.getValueJson(), BarometerDTO.class);

        BarometerData data = new BarometerData();
        data.setId(msg.getId());
        data.setSensorId(msg.getSensorId());
        data.setMeasuredAt(msg.getMeasuredAt());
        data.setDeviceName(msg.getDeviceName());
        data.setAirPressure(dto.airPressure());

        session.persist(data);
    }

    private static void persistLocation(Session session, SensorMessage msg) {
        LocationDTO dto = JsonParser.parse(msg.getValueJson(), LocationDTO.class);

        LocationData data = new LocationData();
        data.setId(msg.getId());
        data.setSensorId(msg.getSensorId());
        data.setMeasuredAt(msg.getMeasuredAt());
        data.setDeviceName(msg.getDeviceName());
        data.setLatitude(dto.latitude());
        data.setLongitude(dto.longitude());

        session.persist(data);
    }

    private static void persistAccelerometer(Session session, SensorMessage msg) {
        AccelerometerDTO dto =
                JsonParser.parse(msg.getValueJson(), AccelerometerDTO.class);

        AccelerometerData data = new AccelerometerData();
        data.setId(msg.getId());
        data.setSensorId(msg.getSensorId());
        data.setMeasuredAt(msg.getMeasuredAt());
        data.setDeviceName(msg.getDeviceName());
        data.setX(dto.x());
        data.setY(dto.y());
        data.setZ(dto.z());

        session.persist(data);
    }
}
