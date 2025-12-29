package consumer;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.teamscore.common.HibernateUtil;
import ru.teamscore.common.SensorType;
import ru.teamscore.common.entity.*;
import ru.teamscore.consumer.SensorDataPersister;
import ru.teamscore.producer.RandomSensorFactory;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SensorDataPersisterTest {
    private static SessionFactory sessionFactory;
    private Session session;

    @BeforeAll
    static void init() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @BeforeEach
    void setUp() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void tearDown() {
        if (session.getTransaction().isActive())
            session.getTransaction().rollback();
        session.close();
    }

    @Test
    void LightDataPersist() {
        SensorMessage msg = RandomSensorFactory.create();
        msg.setSensorType(SensorType.LIGHT);
        int light = 200;
        msg.setValueJson(String.format("{ \"light\": %d }", light));

        SensorDataPersister.persist(session, msg);
        session.flush();
        session.clear();

        LightData data = session.get(LightData.class, msg.getId());
        assertNotNull(data);
        assertEquals(light, data.getLight());
        assertEquals(msg.getDeviceName(), data.getDeviceName());
        assertEquals(msg.getSensorId(), data.getSensorId());
        assertEquals(msg.getMeasuredAt(), data.getMeasuredAt());
    }

    @Test
    void BarometerDataPersist() {
        SensorMessage msg = RandomSensorFactory.create();
        msg.setSensorType(SensorType.BAROMETER);
        double airPressure = 105000.1;
        msg.setValueJson(String.format(Locale.US,
                "{ \"air_pressure\": %f }",
                airPressure));

        SensorDataPersister.persist(session, msg);
        session.flush();
        session.clear();

        BarometerData data = session.get(BarometerData.class, msg.getId());
        assertNotNull(data);
        assertEquals(airPressure, data.getAirPressure());
        assertEquals(msg.getDeviceName(), data.getDeviceName());
        assertEquals(msg.getSensorId(), data.getSensorId());
        assertEquals(msg.getMeasuredAt(), data.getMeasuredAt());
    }

    @Test
    void AccelerometerDataPersist() {
        SensorMessage msg = RandomSensorFactory.create();
        msg.setSensorType(SensorType.ACCELEROMETER);
        double x = 5.8;
        double y = 2.765;
        double z = 0.0001;
        msg.setValueJson(String.format(Locale.US,
                "{ \"x\": %f, \"y\": %f, \"z\": %f }",
                x, y, z));

        SensorDataPersister.persist(session, msg);
        session.flush();
        session.clear();

        AccelerometerData data = session.get(AccelerometerData.class, msg.getId());
        assertNotNull(data);
        assertEquals(x, data.getX());
        assertEquals(y, data.getY());
        assertEquals(z, data.getZ());
        assertEquals(msg.getDeviceName(), data.getDeviceName());
        assertEquals(msg.getSensorId(), data.getSensorId());
        assertEquals(msg.getMeasuredAt(), data.getMeasuredAt());
    }

    @Test
    void LocationDataPersist() {
        SensorMessage msg = RandomSensorFactory.create();
        msg.setSensorType(SensorType.LOCATION);
        double longitude = -15.8;
        double latitude = 76.765;
        msg.setValueJson(String.format(Locale.US,
                "{ \"longitude\": %f, \"latitude\": %f }",
                longitude, latitude));

        SensorDataPersister.persist(session, msg);
        session.flush();
        session.clear();

        LocationData data = session.get(LocationData.class, msg.getId());
        assertNotNull(data);
        assertEquals(longitude, data.getLongitude());
        assertEquals(latitude, data.getLatitude());
        assertEquals(msg.getDeviceName(), data.getDeviceName());
        assertEquals(msg.getSensorId(), data.getSensorId());
        assertEquals(msg.getMeasuredAt(), data.getMeasuredAt());
    }
}
