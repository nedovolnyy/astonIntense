package UnitTests.controller;

import com.controller.UserController;
import com.dto.UserDto;
import com.entity.User;
import com.service.UserService;
import com.utils.enums.Operation;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.mockito.ArgumentMatchers.any;
import org.springframework.boot.hibernate.autoconfigure.HibernateJpaAutoConfiguration;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = UserController.class)
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = {
    DataSourceAutoConfiguration.class,
    HibernateJpaAutoConfiguration.class
})
public class UserControllerTest {

    public static List<User> testUserList = List.of(
            new User(1, "Ивцев Иоан Казимирович", "ivy@dmail.su", 47, LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)),
            new User(2, "Второй Иоан Казимирович", "ivy2@dmail.su", 42, LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)),
            new User(3, "Третий Иоан Казимирович", "ivy3@dmail.su", 43, LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)));

    public static List<UserDto> testUserDtoList = testUserList.stream()
            .map(UserDto::new)
            .collect(Collectors.toList());

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    public UserControllerTest() throws JsonProcessingException {
    }

    @Test
    public void getAll_whenReturnUsers_shouldReturnAllUsers() throws Exception {
        Mockito.when(userService.getAll()).thenReturn(testUserDtoList);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(testUserDtoList.size()));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void getById_whenReturnById_shouldReturnUserById(int expectedIndex) throws Exception {
        Mockito.when(userService.getById(expectedIndex))
                .thenReturn(testUserDtoList.get(expectedIndex));
        var expectedDto = testUserDtoList.get(expectedIndex);

        mockMvc.perform(get("/api/v1/users/{id}", expectedIndex))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expectedDto.getName()))
                .andExpect(jsonPath("$.email").value(expectedDto.getEmail()))
                .andExpect(jsonPath("$.age").value(expectedDto.getAge()));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void save_whenCallSaveUser_shouldSaveUser(int expectedIndex) throws Exception {
        Mockito.when(userService.save(testUserDtoList.get(expectedIndex))).thenReturn(Operation.INSERT);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testUserDtoList.get(expectedIndex))))
                .andExpect(status().isOk());
    }

    @Test
    public void save_whenErrorSaveUser_shouldReturnBadRequest() throws Exception {
        Mockito.when(userService.save(any(UserDto.class))).thenReturn(Operation.ERROR);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testUserDtoList.get(any(Integer.class)))))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void update_whenCallUpdateUser_shouldUpdateUser(int expectedIndex) throws Exception {
        Mockito.when(userService.update(testUserDtoList.get(expectedIndex), expectedIndex))
                .thenReturn(Operation.UPDATE);

        mockMvc.perform(put("/api/v1/users/{id}", expectedIndex)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testUserDtoList.get(expectedIndex))))
                .andExpect(status().isOk());
    }
    
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void update_whenErrorUpdateUser_shouldReturnBadRequest(int expectedIndex) throws Exception {
        Mockito.when(userService.update(any(UserDto.class), any(Integer.class)))
                .thenReturn(Operation.ERROR);

        mockMvc.perform(put("/api/v1/users/{id}", expectedIndex)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testUserDtoList.get(expectedIndex))))
                .andExpect(status().isBadRequest());
    }
    
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void delete_whenCallDeleteUser_shouldDeleteUser(int expectedIndex) throws Exception {
        Mockito.when(userService.delete(expectedIndex)).thenReturn(Operation.DELETE);

        mockMvc.perform(delete("/api/v1/users/{id}", expectedIndex))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void delete_whenErrorDeleteUser_shouldReturnBadRequest(int expectedIndex) throws Exception {
        Mockito.when(userService.delete(expectedIndex)).thenReturn(Operation.ERROR);

        mockMvc.perform(delete("/api/v1/users/{id}", expectedIndex))
                .andExpect(status().isBadRequest());
    }
}
