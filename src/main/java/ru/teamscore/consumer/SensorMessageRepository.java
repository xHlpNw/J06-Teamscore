package ru.teamscore.consumer;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import ru.teamscore.common.entity.SensorMessage;

import java.util.List;

@AllArgsConstructor
public class SensorMessageRepository {
    public static List<SensorMessage> findUnprocessed(EntityManager em, int limit) {
        return em.createQuery(
                """
                    SELECT sm
                    FROM SensorMessage sm
                    WHERE sm.id NOT IN (
                        SELECT pm.messageId from ProcessedMessage pm
                    )
                    ORDER BY sm.savedAt
                        """,
                SensorMessage.class
        ).setMaxResults(limit).getResultList();
    }
}
