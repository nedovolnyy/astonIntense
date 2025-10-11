package UnitTests.controller;

import com.userservice.UserApplication;
import com.userservice.controller.UserController;
import com.userservice.dto.UserDto;
import com.userservice.entity.User;
import com.userservice.service.UserService;
import com.userservice.utils.enums.OperationType;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
import static org.mockito.Mockito.when;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import tools.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = UserApplication.class)
class UserControllerTest {

    @MockitoBean
    private UserService userService;
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    public final static List<User> TEST_USERSLIST = List.of(
            new User(1, "Ивцев Иоан Казимирович", "ivy@dmail.su", 47, LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)),
            new User(2, "Второй Иоан Казимирович", "ivy2@dmail.su", 42, LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)),
            new User(3, "Третий Иоан Казимирович", "ivy3@dmail.su", 43, LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)));

    public static List<UserDto> testUserDtoList = TEST_USERSLIST.stream()
            .map(UserDto::new)
            .collect(Collectors.toList());

    public UserControllerTest() throws JsonProcessingException {
    }

    @Test
    public void getAll_whenReturnUsers_shouldReturnAllUsers() throws Exception {
        when(userService.getAll()).thenReturn(testUserDtoList);

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(testUserDtoList.size()));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void getById_whenReturnById_shouldReturnUserById(int expectedIndex) throws Exception {
        when(userService.getById(expectedIndex))
                .thenReturn(testUserDtoList.get(expectedIndex));
        var expectedDto = testUserDtoList.get(expectedIndex);

        mockMvc.perform(get("/api/v1/users/{id}", expectedIndex))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expectedDto.name()))
                .andExpect(jsonPath("$.email").value(expectedDto.email()))
                .andExpect(jsonPath("$.age").value(expectedDto.age()));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void save_whenCallSaveUser_shouldSaveUser(int expectedIndex) throws Exception {
        when(userService.save(testUserDtoList.get(expectedIndex))).thenReturn(OperationType.CREATE);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUserDtoList.get(expectedIndex))))
                .andExpect(status().isCreated());
    }

    @Test
    public void save_whenErrorSaveUser_shouldReturnBadRequest() throws Exception {
        when(userService.save(any(UserDto.class))).thenReturn(OperationType.ERROR);

        mockMvc.perform(post("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUserDtoList.get(any(Integer.class)))))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void update_whenCallUpdateUser_shouldUpdateUser(int expectedIndex) throws Exception {
        when(userService.update(testUserDtoList.get(expectedIndex), expectedIndex))
                .thenReturn(OperationType.UPDATE);

        mockMvc.perform(put("/api/v1/users/{id}", expectedIndex)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUserDtoList.get(expectedIndex))))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void update_whenErrorUpdateUser_shouldReturnBadRequest(int expectedIndex) throws Exception {
        when(userService.update(any(UserDto.class), any(Integer.class)))
                .thenReturn(OperationType.ERROR);

        mockMvc.perform(put("/api/v1/users/{id}", expectedIndex)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testUserDtoList.get(expectedIndex))))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void delete_whenCallDeleteUser_shouldDeleteUser(int expectedIndex) throws Exception {
        when(userService.delete(expectedIndex)).thenReturn(OperationType.DELETE);

        mockMvc.perform(delete("/api/v1/users/{id}", expectedIndex))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    public void delete_whenErrorDeleteUser_shouldReturnBadRequest(int expectedIndex) throws Exception {
        when(userService.delete(expectedIndex)).thenReturn(OperationType.ERROR);

        mockMvc.perform(delete("/api/v1/users/{id}", expectedIndex))
                .andExpect(status().isBadRequest());
    }
}
