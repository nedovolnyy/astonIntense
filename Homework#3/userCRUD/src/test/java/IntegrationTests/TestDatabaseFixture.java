/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IntegrationTests;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.Getter;
import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.testcontainers.containers.PostgreSQLContainer;

/**
 *
 * @author AKrot
 */
@Getter
public class TestDatabaseFixture {

    protected static PostgreSQLContainer<?> postgreSQLContainer;
    public static SessionFactory sessionFactory;
    public static List<User> testUserList = List.of(
            new User("Ивцев Иоан Казимирович", "ivy@dmail.su", 47, LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)),
            new User("Второй Иоан Казимирович", "ivy2@dmail.su", 42, LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)),
            new User("Третий Иоан Казимирович", "ivy3@dmail.su", 43, LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)));

    public void buildSession() {
        postgreSQLContainer = new PostgreSQLContainer<>("postgres:17")
                .withDatabaseName("testUserDB")
                .withUsername("admin")
                .withPassword("admin");
        postgreSQLContainer.start();

        org.hibernate.cfg.Configuration configuration = new Configuration();
        configuration.setProperty("hibernate.connection.url", postgreSQLContainer.getJdbcUrl());
        configuration.setProperty("hibernate.connection.username", postgreSQLContainer.getUsername());
        configuration.setProperty("hibernate.connection.password", postgreSQLContainer.getPassword());
        configuration.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        configuration.addAnnotatedClass(User.class);

        var registry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties())
                .build();

        sessionFactory = configuration.buildSessionFactory(registry);
    }

    public void stopSession() {
        if (postgreSQLContainer != null) {
            postgreSQLContainer.stop();
        }
    }

    public void fillDb() {
        try (var session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            session.createNativeMutationQuery("DELETE FROM \"user\"").executeUpdate();
            for (var user : testUserList) {
                user.setId(null);
                session.persist(user);
            }
            session.getTransaction().commit();
        }
    }
}
