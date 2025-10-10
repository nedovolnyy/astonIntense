package IntegrationTests.controller;

import com.notificationservice.NotificationServiceApplication;
import com.notificationservice.consumer.KafkaConsumerConfiguration;
import com.notificationservice.consumer.NotificationKafkaListener;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.test.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.client.RestClient;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@EnableAutoConfiguration
@SpringBootTest(classes = NotificationServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@Testcontainers
public class NotificationControllerTest {

    @MockitoBean
    KafkaConsumerConfiguration kafkaConsumerConfiguration;
    @MockitoBean
    NotificationKafkaListener notificationKafkaListener;

    @Container
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
                        + mailpitContainer.getMappedPort(8025) + "/api/v1")
                .build();
    }

    @Test
    void sendCreateMessage_withValidData_ReturnsOK() {
        var headers = new  HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var requestEntity = new HttpEntity<>("test@test.test", headers);

        var response = restTemplate.postForEntity(
                "/api/v1/notification/send-create-message",
                requestEntity,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        var messages = mailpitClient.get()
                .uri("/messages")
                .retrieve()
                .body(String.class);

        assertNotNull(messages);
        assertTrue(messages.contains("test@test.test"));
    }

    @Test
    void sendDeleteMessage_withValidData_ReturnsOK() {
        var headers = new  HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        var requestEntity = new HttpEntity<>("test@test.test", headers);

        var response = restTemplate.postForEntity(
                "/api/v1/notification/send-delete-message",
                requestEntity,
                String.class
        );

        assertEquals(HttpStatus.OK, response.getStatusCode());

        var messages = mailpitClient.get()
                .uri("/messages")
                .retrieve()
                .body(String.class);

        assertNotNull(messages);
        assertTrue(messages.contains("test@test.test"));
    }
}
