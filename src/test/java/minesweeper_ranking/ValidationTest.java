package minesweeper_ranking;

import minesweeper_ranking.model.LoginCredentials;
import minesweeper_ranking.model.ResponseMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ValidationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldReturnOkWhenCredentialsAreProper() {
        ResponseEntity<ResponseMessage> response = restTemplate
                .postForEntity("/api/register", new LoginCredentials("Wwww", "pass"), ResponseMessage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Registered successfully");
    }

    @Test
    void shouldReturnBadRequestWhenUsernameIsTooShort() {
        ResponseEntity<ResponseMessage> response = restTemplate
                .postForEntity("/api/register", new LoginCredentials("Abc", "pass"), ResponseMessage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Username must be between 4 and 12 characters.");
    }

    @Test
    void shouldReturnBadRequestWhenUsernameIsTooLong() {
        ResponseEntity<ResponseMessage> response = restTemplate
                .postForEntity("/api/register", new LoginCredentials(generateProperTooLongString(13), "pass"), ResponseMessage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Username must be between 4 and 12 characters.");
    }

    @Test
    void shouldReturnBadRequestWhenUsernameContainsNumbers() {
        ResponseEntity<ResponseMessage> response = restTemplate
                .postForEntity("/api/register", new LoginCredentials("Aaa1231", "pass"), ResponseMessage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Username can contain only alphabet characters.");
    }

    @Test
    void shouldReturnBadRequestWhenUsernameStartsWithLowercase() {
        ResponseEntity<ResponseMessage> response = restTemplate
                .postForEntity("/api/register", new LoginCredentials("aaaa", "pass"), ResponseMessage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Username must start with a capital character.");
    }

    @Test
    void shouldReturnBadRequestWhenPasswordIsTooShort() {
        ResponseEntity<ResponseMessage> response = restTemplate
                .postForEntity("/api/register", new LoginCredentials("Username", "pas"), ResponseMessage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Password must be between 4 and 30 characters.");
    }

    @Test
    void shouldReturnBadRequestWhenPasswordIsTooLong() {
        ResponseEntity<ResponseMessage> response = restTemplate
                .postForEntity("/api/register", new LoginCredentials("Username", generateProperTooLongString(31)), ResponseMessage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Password must be between 4 and 30 characters.");
    }

    @Test
    void shouldReturnBadRequestWhenUsernameStartsWithLowercaseAndContainsNumbersAndIsTooShortAndPasswordIsTooLong() {
        ResponseEntity<ResponseMessage> response = restTemplate
                .postForEntity("/api/register", new LoginCredentials("a12", generateProperTooLongString(31)), ResponseMessage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).contains("Password must be between 4 and 30 characters.");
        assertThat(response.getBody().getMessage()).contains("Username must start with a capital character.");
        assertThat(response.getBody().getMessage()).contains("Username must be between 4 and 12 characters.");
        assertThat(response.getBody().getMessage()).contains("Username can contain only alphabet characters.");
    }

    private String generateProperTooLongString(int length) {
        StringBuilder stringBuilder = new StringBuilder("A");
        for (int i = 0; i < length - 1; i++) {
            stringBuilder.append("a");
        }
        return stringBuilder.toString();
    }


}
