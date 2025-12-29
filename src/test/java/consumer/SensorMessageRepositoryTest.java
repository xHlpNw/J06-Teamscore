package consumer;

import jakarta.persistence.EntityManager;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.teamscore.common.HibernateUtil;
import ru.teamscore.common.entity.ProcessedMessage;
import ru.teamscore.common.entity.SensorMessage;
import ru.teamscore.consumer.SensorMessageRepository;
import ru.teamscore.producer.RandomSensorFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class SensorMessageRepositoryTest {
    private static SessionFactory sessionFactory =
            HibernateUtil.getSessionFactory();
    private EntityManager em;
    private SensorMessageRepository repository;

    @BeforeEach
    void setUp() {
        em = sessionFactory
                .openSession()
                .unwrap(EntityManager.class);
        repository = new SensorMessageRepository(em);
        em.getTransaction().begin();

        SensorMessage first = RandomSensorFactory.create();
        SensorMessage second = RandomSensorFactory.create();
        SensorMessage third = RandomSensorFactory.create();

        em.persist(first);
        em.persist(second);
        em.persist(third);

        ProcessedMessage pm = new ProcessedMessage(
                second.getId(),
                first.getSavedAt().plusSeconds(30));

        em.persist(pm);
        em.getTransaction().commit();
    }

    @AfterEach
    void tearDown() {
        if (em.getTransaction().isActive()) em.getTransaction().rollback();
        em.close();
    }

    @Test
    void returnsOnlyUnprocessedMessages() {
        em.getTransaction().begin();
        List<SensorMessage> results = repository.findUnprocessed(10);
        em.getTransaction().commit();
        assertEquals(2, results.size());
    }
}
