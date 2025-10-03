package IntegrationTests.controller;

import com.userservice.UserApplication;
import com.userservice.dto.UserDto;
import com.userservice.entity.User;
import com.userservice.repository.UserRepository;
import java.sql.DriverManager;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.ClassRule;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.web.server.test.LocalServerPort;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import static org.springframework.http.HttpHeaders.ACCEPT;
import org.springframework.http.MediaType;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;

@ContextConfiguration(initializers = {UserControllerTest.Initializer.class})
@SpringBootTest(classes = UserApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class UserControllerTest {

    @LocalServerPort
    int localServerPort;

    @Autowired
    private final WebTestClient webTestClient = WebTestClient.bindToServer()
            .baseUrl("http://localhost:" + localServerPort).build();

    @ClassRule
    protected static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:17")
            .withDatabaseName("testUserDB")
            .withUsername("admin")
            .withPassword("admin")
            .withInitScript("testUserDB.sql");

    @Autowired
    private UserRepository userRepository;

    public final List<User> TEST_USERSLIST = List.of(
            new User(1, "Ивцев Иоан Казимирович", "ivy@dmail.su", 47, LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)),
            new User(2, "Второй Иоан Казимирович", "ivy2@dmail.su", 42, LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)),
            new User(3, "Третий Иоан Казимирович", "ivy3@dmail.su", 43, LocalDateTime.now().truncatedTo(ChronoUnit.MICROS)));

    public final List<UserDto> testUserDtoList = TEST_USERSLIST.stream()
            .map(UserDto::new)
            .collect(Collectors.toList());

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @BeforeAll
    public static void setup() {
        postgreSQLContainer.start();
    }

    @BeforeEach
    public void fillDb() throws Exception {
        try (var connection = DriverManager
                .getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())) {
            for (var user : TEST_USERSLIST) {
                var prStmnt = connection.prepareStatement("INSERT INTO \"user\"(id, name, email, age) VALUES(?, ?, ?, ?)");
                prStmnt.setInt(1, user.getId());
                prStmnt.setString(2, user.getName());
                prStmnt.setString(3, user.getEmail());
                prStmnt.setInt(4, user.getAge());
                prStmnt.executeUpdate();
            }
        }
    }

    @AfterEach
    public void clearDb() throws Exception {
        try (var connection = DriverManager
                .getConnection(postgreSQLContainer.getJdbcUrl(), postgreSQLContainer.getUsername(), postgreSQLContainer.getPassword())) {
            var prStmnt = connection.prepareStatement("DELETE FROM \"user\"");
            prStmnt.executeUpdate();
        }
    }

    @Test
    public void getAll_whenReturnUsers_shouldReturnAllUsers() throws Exception {
        webTestClient.get().uri("/api/v1/users")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(testUserDtoList.size());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void getById_whenReturnById_shouldReturnUserById(int expectedIndex) throws Exception {
        var expectedDto = testUserDtoList.get(expectedIndex - 1);

        webTestClient.get().uri("/api/v1/users/{id}", expectedIndex)
                .header(ACCEPT, APPLICATION_JSON_VALUE)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .jsonPath("$.name").isEqualTo(expectedDto.name())
                .jsonPath("$.email").isEqualTo(expectedDto.email())
                .jsonPath("$.age").isEqualTo(expectedDto.age());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void save_whenCallSaveUser_shouldSaveUser(int expectedIndex) throws Exception {
        var actualDBSize = ((Collection<?>) userRepository.findAll()).size();

        webTestClient.post().uri("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(testUserDtoList.get(expectedIndex - 1))//testUserDto)
                .exchange()
                .expectStatus().is2xxSuccessful();

        var expectedDBSize = ((Collection<?>) userRepository.findAll()).size();

        assertThat(actualDBSize).isEqualTo(expectedDBSize - 1);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void update_whenCallUpdateUser_shouldUpdateUser(int expectedIndex) throws Exception {
        var expectedUserDto = new UserDto("test", "test", 999);

        webTestClient.put().uri("/api/v1/users/{id}", expectedIndex)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(expectedUserDto)
                .exchange()
                .expectStatus().is2xxSuccessful();

        var actualUserDTO = new UserDto(userRepository.findById(expectedIndex).orElseThrow());

        assertThat(actualUserDTO).isEqualTo(expectedUserDto);
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    public void delete_whenCallDeleteUser_shouldDeleteUser(int expectedIndex) throws Exception {
        var actualDBSize = ((Collection<?>) userRepository.findAll()).size();

        webTestClient.delete().uri("/api/v1/users/{id}", expectedIndex)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().is2xxSuccessful();

        var expectedDBSize = ((Collection<?>) userRepository.findAll()).size();

        assertThat(actualDBSize).isEqualTo(expectedDBSize + 1);
    }
}
