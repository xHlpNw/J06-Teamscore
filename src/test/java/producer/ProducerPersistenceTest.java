package producer;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;
import ru.teamscore.common.HibernateUtil;
import ru.teamscore.common.entity.SensorMessage;
import ru.teamscore.producer.RandomSensorFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProducerPersistenceTest {
    @Test
    void sensorMessageIsPersisted() {
        SensorMessage msg = RandomSensorFactory.create();

        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(msg);
            transaction.commit();
        }

        SensorMessage loadedMsg;

        try (Session session = HibernateUtil
                .getSessionFactory().openSession()) {
            loadedMsg = session.get(SensorMessage.class, msg.getId());
        }

        assertNotNull(loadedMsg);
        assertEquals(msg.getSensorId(), loadedMsg.getSensorId());
        assertEquals(msg.getSensorType(), loadedMsg.getSensorType());
        assertEquals(msg.getDeviceName(), loadedMsg.getDeviceName());
        assertEquals(msg.getValueJson(), loadedMsg.getValueJson());
        assertEquals(msg.getMeasuredAt(), loadedMsg.getMeasuredAt());
        assertEquals(msg.getSavedAt(), loadedMsg.getSavedAt());
    }
}
