/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IntegrationTests;

import static IntegrationTests.TestDatabaseFixture.*;
import java.sql.SQLException;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.*;
import repository.*;

/**
 *
 * @author AKrot
 */
public class UserDaoImplTests {

    private static final TestDatabaseFixture testDatabaseFixture = new TestDatabaseFixture();
    private static UserDao userDao;

    @BeforeAll
    public static void start() {
        testDatabaseFixture.buildSession();
        userDao = new UserDaoImpl(sessionFactory);
    }

    @AfterAll
    public static void stop() {
        testDatabaseFixture.stopSession();
    }

    @BeforeEach
    public void fillDb() {
        testDatabaseFixture.fillDb();
    }

    @Test
    public void getById_getUserById() throws SQLException {
        var expectedUser = testUserList.get(1);

        var actualUser = userDao.getById(testUserList.get(1).getId());

        assertThat(actualUser).isEqualTo(expectedUser);
    }

    @Test
    public void getById_getNotFoundUser() throws SQLException {
        var actualUser = userDao.getById(-1);
        
        assertThat(actualUser).isNull();
    }

    @Test
    public void getAll_getAllUsers() throws SQLException {
        var actualUsers = userDao.getAll();

        assertThat(actualUsers).isEqualTo(testUserList);
    }
    
    @Test
    public void insert_whenInsertUser_ShouldBeEqualSameUser() throws SQLException {
        var expectedUser = testUserList.get(1);
        
        userDao.insert(expectedUser);
        var actualUsers = userDao.getAll();
        
        assertThat(actualUsers).contains(expectedUser).usingRecursiveComparison().ignoringFields("Id");
    }
    
    @Test
    public void update_whenUpdateUser_ShouldBeEqualSameUser() throws SQLException {
        var expectedUser = testUserList.get(2);
        expectedUser.setName("Тестов Тест Тестович");
        expectedUser.setEmail("test@test.test");
        
        userDao.update(expectedUser);
        var actualUser = userDao.getById(expectedUser.getId());
        
        assertThat(actualUser).isEqualTo(expectedUser);
    }
    
    @Test
    public void delete_whenDeleteUser_ShouldBeDeleted() throws SQLException {
        var expectedCount = testUserList.size() - 1;
        
        userDao.delete(testUserList.get(1));
        var actualCount = userDao.getAll().size();
        
        assertThat(actualCount).isEqualTo(expectedCount);
    }
}
