package ru.teamscore.consumer;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.teamscore.common.HibernateUtil;
import ru.teamscore.common.entity.ProcessedMessage;
import ru.teamscore.common.entity.SensorMessage;

import java.time.LocalDateTime;
import java.util.List;

public class ConsumerService {

    public void run() {
        while (true) {
            try {
                processBatch();
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void processBatch() {
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {
            List<SensorMessage> messages = SensorMessageRepository
                    .findUnprocessed(session.unwrap(EntityManager.class),20);

            for (SensorMessage msg : messages) {
                processSingleMessage(msg);
            }
        }
    }

    private void processSingleMessage(SensorMessage message) {
        try (Session session =
                     HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            SensorDataPersister.persist(session, message);

            ProcessedMessage processed = new ProcessedMessage(
                    message.getId(),
                    LocalDateTime.now());
            session.persist(processed);

            transaction.commit();
        }
    }
}
