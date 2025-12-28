package producer;

import org.hibernate.Session;
import org.hibernate.Transaction;
import ru.teamscore.common.HibernateUtil;
import ru.teamscore.common.entity.SensorMessage;
import ru.teamscore.producer.RandomSensorFactory;

import java.util.Random;

public class SensorMessageGenerator {
    private final Random rand = new Random();

    public void run() {
        while (true) {
            try (
                    Session session = HibernateUtil
                            .getSessionFactory()
                            .openSession()
            ) {
                SensorMessage msg = RandomSensorFactory.create();
                Transaction transaction = session.beginTransaction();
                session.persist(msg);
                transaction.commit();
                Thread.sleep(100 + rand.nextInt(10000));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
