package aggregator;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.teamscore.aggregator.AggregationInterval;
import ru.teamscore.aggregator.AggregationRepository;
import ru.teamscore.aggregator.AggregationRow;
import ru.teamscore.common.HibernateUtil;
import ru.teamscore.common.SensorType;
import ru.teamscore.common.entity.LightData;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class AggregationRepositoryTest {
    private final static SessionFactory sessionFactory =
            HibernateUtil.getSessionFactory();
    private AggregationRepository repository;

    @BeforeEach
    void setUp() {
        repository = new AggregationRepository();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            LightData d1 = new LightData();
            d1.setId(UUID.randomUUID());
            d1.setSensorId(UUID.randomUUID());
            d1.setDeviceName("dev1");
            d1.setMeasuredAt(LocalDateTime.of(2024,1,1,10,0));
            d1.setLight(100);

            LightData d2 = new LightData();
            d2.setId(UUID.randomUUID());
            d2.setSensorId(UUID.randomUUID());
            d2.setDeviceName("dev1");
            d2.setMeasuredAt(LocalDateTime.of(2024,1,1,10,1));
            d2.setLight(200);

            LightData d3 = new LightData();
            d3.setId(UUID.randomUUID());
            d3.setSensorId(UUID.randomUUID());
            d3.setDeviceName("dev1");
            d3.setMeasuredAt(LocalDateTime.of(2024,1,1,10,0, 30));
            d3.setLight(300);

            LightData d4 = new LightData();
            d4.setId(UUID.randomUUID());
            d4.setSensorId(UUID.randomUUID());
            d4.setDeviceName("dev1");
            d4.setMeasuredAt(LocalDateTime.of(2024,1,1,12,0, 30));
            d4.setLight(400);

            LightData d5 = new LightData();
            d5.setId(UUID.randomUUID());
            d5.setSensorId(UUID.randomUUID());
            d5.setDeviceName("dev1");
            d5.setMeasuredAt(LocalDateTime.of(2024,1,3,10,0, 30));
            d5.setLight(300);

            LightData d6 = new LightData();
            d6.setId(UUID.randomUUID());
            d6.setSensorId(UUID.randomUUID());
            d6.setDeviceName("dev1");
            d6.setMeasuredAt(LocalDateTime.of(2024,1,13,10,0, 30));
            d6.setLight(300);

            LightData d7 = new LightData();
            d7.setId(UUID.randomUUID());
            d7.setSensorId(UUID.randomUUID());
            d7.setDeviceName("dev2");
            d7.setMeasuredAt(LocalDateTime.of(2024,1,1,10,0, 30));
            d7.setLight(300);

            session.persist(d1);
            session.persist(d2);
            session.persist(d3);
            session.persist(d4);
            session.persist(d5);
            session.persist(d6);
            session.persist(d7);

            session.getTransaction().commit();
        }
    }


    @AfterEach
    void tearDown() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createMutationQuery("delete from LightData").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void testAggregateLightMinuteInterval() {
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 10, 0);
        LocalDateTime to = LocalDateTime.of(2024, 1, 1, 10, 2);

        List<AggregationRow> result = repository.aggregate(
                SensorType.LIGHT,
                from,
                to,
                AggregationInterval.MINUTE,
                null
        );

        for (AggregationRow row : result) {
            System.out.println(row.intervalStart());
        }

        assertNotNull(result);
        assertEquals(3, result.size());

        for (AggregationRow row : result) {
            assertNotNull(row.deviceName());
            assertNotNull(row.intervalStart());
            assertEquals(1, row.avgValues().length);
        }
    }

    @Test
    void testAggregateLightDayInterval() {
        LocalDateTime from = LocalDateTime.of(2024, 1, 1, 10, 0);
        LocalDateTime to = LocalDateTime.of(2024, 1, 4, 10, 0);

        List<AggregationRow> result = repository.aggregate(
                SensorType.LIGHT,
                from,
                to,
                AggregationInterval.DAY,
                null
        );

        for (AggregationRow row : result) {
            System.out.println(row.intervalStart());
        }

        assertNotNull(result);
        assertEquals(3, result.size());

        for (AggregationRow row : result) {
            assertNotNull(row.deviceName());
            assertNotNull(row.intervalStart());
            assertEquals(1, row.avgValues().length);
        }
    }
}
