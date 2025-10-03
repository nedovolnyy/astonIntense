package IntegrationTests.controller;

import com.notificationservice.NotificationServiceApplication;
import com.notificationservice.dto.MessageDto;
import com.notificationservice.utils.enums.OperationType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import lombok.RequiredArgsConstructor;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.test.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

//@EnableAutoConfiguration(exclude = {KafkaAutoConfiguration.class})
@RequiredArgsConstructor
@SpringBootTest(classes = NotificationServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class NotificationControllerTest {

    static final GenericContainer<?> mailpitContainer = new GenericContainer<>("axllent/mailpit:v1.27")
            .withExposedPorts(1025, 8025)
            .waitingFor(Wait.forLogMessage(".*accessible via.*", 1));

    private static RestClient mailpitClient;

    @Autowired
    private TestRestTemplate restTemplate;

    @DynamicPropertySource
    static void configureMail(DynamicPropertyRegistry registry) {
        registry.add("spring.mail.host", mailpitContainer::getHost);
        registry.add("spring.mail.port", mailpitContainer::getFirstMappedPort);
        registry.add("mailpit.web.port", () -> mailpitContainer.getMappedPort(8025));
    }

    @BeforeAll
    static void setup() {
        mailpitClient = RestClient.builder()
                .baseUrl("http://" + mailpitContainer.getHost() + ":"
                        + mailpitContainer.getMappedPort(8025) + "/api/v1/notification")
                .build();
    }

    @Test
    void sendCreateMessage_withValidData_ReturnsOK() {
        var messageDto = new MessageDto(OperationType.CREATE, "test@test.test");

        var response = restTemplate.postForEntity(
                "/send-create-message",
                messageDto,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        var messages = mailpitClient.get()
                .uri("/messages")
                .retrieve()
                .body(String.class);

        assertNotNull(messages);
        assertTrue(messages.contains("test@test.test"));
    }

    @Test
    void sendDeleteMessage_withValidData_ReturnsOK() {
        var messageDto = new MessageDto(OperationType.DELETE, "test@test.test");

        var response = restTemplate.postForEntity(
                "/send-delete-message",
                messageDto,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        var messages = mailpitClient.get()
                .uri("/messages")
                .retrieve()
                .body(String.class);

        assertNotNull(messages);
        assertTrue(messages.contains("test@test.test"));
    }
}
