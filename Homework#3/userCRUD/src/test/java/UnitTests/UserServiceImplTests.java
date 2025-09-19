/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UnitTests;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import model.User;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import repository.UserDao;
import service.UserService;
import service.UserServiceImpl;
import utils.enums.Operation;

/**
 *
 * @author AKrot
 */
public class UserServiceImplTests {

    private final UserDao mockUserDao = Mockito.mock(UserDao.class);
    private final UserService userService = new UserServiceImpl(mockUserDao);
    private final User expectedUser = new User("Ивцев Иоан Казимирович", "ivy@dmail.su", 47, LocalDateTime.now());

    @Test
    public void getById_whenReturnById_shouldUserNotNull() throws SQLException {
        Mockito.when(mockUserDao.getById(expectedUser.getId())).thenReturn(expectedUser);
        var actualUser = userService.getById(expectedUser.getId());

        assertThat(actualUser).isNotNull();
    }

    @Test
    public void getAll_whenReturnUsers_shouldUsersSizeNotZero() throws SQLException {
        var expectedUserList = List.of(expectedUser, expectedUser, expectedUser, expectedUser, expectedUser);
        Mockito.when(mockUserDao.getAll()).thenReturn(expectedUserList);

        var actualUsers = userService.getAll().size();

        assertThat(actualUsers).isNotEqualTo(0);
    }

    @Test
    public void insert_whenCallInsertUser_shouldReturnOperationInsert() throws SQLException {
        Mockito.when(mockUserDao.insert(expectedUser)).thenReturn(Operation.INSERT);
        
        var actualOperation = userService.insert(expectedUser);
        
        assertThat(actualOperation).isEqualTo(Operation.INSERT);
    }

    @Test
    public void insert_whenCallErrorInsertUser_shouldReturnOperationQuit() throws SQLException {
        Mockito.when(mockUserDao.insert(expectedUser)).thenReturn(Operation.QUIT);
        
        var actualOperation = userService.insert(expectedUser);
        
        assertThat(actualOperation).isEqualTo(Operation.QUIT);
    }

    @Test
    public void update_whenCallUpdateUser_shouldReturnOperationUpdate() throws SQLException {
        Mockito.when(mockUserDao.update(expectedUser)).thenReturn(Operation.UPDATE);
        
        var actualOperation = userService.update(expectedUser);
        
        assertThat(actualOperation).isEqualTo(Operation.UPDATE);
    }

    @Test
    public void update_whenCallErrorUpdateUser_shouldReturnOperationQuit() throws SQLException {
        Mockito.when(mockUserDao.update(expectedUser)).thenReturn(Operation.QUIT);
        
        var actualOperation = userService.update(expectedUser);
        
        assertThat(actualOperation).isEqualTo(Operation.QUIT);
    }

    @Test
    public void delete_whenCallDeleteUser_shouldReturnOperationDelete() throws SQLException {
        Mockito.when(mockUserDao.delete(expectedUser)).thenReturn(Operation.DELETE);
        
        var actualOperation = userService.delete(expectedUser);
        
        assertThat(actualOperation).isEqualTo(Operation.DELETE);
    }

    @Test
    public void delete_whenCallErrorDeleteUser_shouldReturnOperationQuit() throws SQLException {
        Mockito.when(mockUserDao.delete(expectedUser)).thenReturn(Operation.QUIT);
        
        var actualOperation = userService.delete(expectedUser);
        
        assertThat(actualOperation).isEqualTo(Operation.QUIT);
    }

}
