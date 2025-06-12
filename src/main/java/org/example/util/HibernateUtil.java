package org.example.util;

import org.example.entity.*;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure();

            // 💡 Registriere hier ALLE @Entity-Klassen manuell:
            configuration.addAnnotatedClass(Hotel.class);
            configuration.addAnnotatedClass(Amenity.class);
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(Occupancy.class);
            configuration.addAnnotatedClass(Benutzer.class);

            return configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError("Initial SessionFactory creation failed: " + ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}