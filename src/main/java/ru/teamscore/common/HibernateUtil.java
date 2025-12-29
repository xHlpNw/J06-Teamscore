package ru.teamscore.common;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.teamscore.common.entity.*;

public class HibernateUtil {
    private static final SessionFactory SESSION_FACTORY = build();

    private static SessionFactory build() {
        try {
            return new Configuration()
                    .addAnnotatedClass(SensorMessage.class)
                    .addAnnotatedClass(ProcessedMessage.class)
                    .addAnnotatedClass(LightData.class)
                    .addAnnotatedClass(BarometerData.class)
                    .addAnnotatedClass(LocationData.class)
                    .addAnnotatedClass(AccelerometerData.class)
                    .buildSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    private HibernateUtil() {}
}
