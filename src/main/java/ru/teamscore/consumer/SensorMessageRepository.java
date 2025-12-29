package ru.teamscore.consumer;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import ru.teamscore.common.entity.SensorMessage;

import java.util.List;

@AllArgsConstructor
public class SensorMessageRepository {
    private final EntityManager manager;

    public List<SensorMessage> findUnprocessed(int limit) {
        return manager.createQuery(
                """
                    SELECT sm
                    FROM SensorMessage sm
                    WHERE sm.id NOT EXISTS (
                        SELECT pm.messageId from ProcessedMessage pm
                    )
                    ORDER BY sm.savedAt
                        """,
                SensorMessage.class
        ).setMaxResults(limit).getResultList();
    }
}
