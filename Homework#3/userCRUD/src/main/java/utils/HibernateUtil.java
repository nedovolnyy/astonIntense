package utils;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 *
 * @author AKrot
 */
public class HibernateUtil {

    private HibernateUtil() {
    }
    
    @Getter
    private static final SessionFactory sessionFactory = buildSessionFactory();

    public static void shutDown() {
        if (sessionFactory != null && sessionFactory.isOpen()) {
            sessionFactory.close();
        }
    }

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();
            Metadata metadata = new MetadataSources(serviceRegistry)
                    .getMetadataBuilder()
                    .build();
            return metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Initial SessionFactory creation failed: " +
                    e.getMessage());
        }
    }
}