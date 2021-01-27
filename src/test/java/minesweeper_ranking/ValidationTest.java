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
    void validationExceptionWhenUsernameIsTooShort() {
        ResponseEntity<ResponseMessage> response = restTemplate
                .postForEntity("/api/register", new LoginCredentials("Abc", "pass"), ResponseMessage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Username must be between 4 and 12 characters.");
    }

    @Test
    void validationExceptionWhenUsernameIsTooLong() {
        ResponseEntity<ResponseMessage> response = restTemplate
                .postForEntity("/api/register", new LoginCredentials(generateProperTooLongString(13), "pass"), ResponseMessage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Username must be between 4 and 12 characters.");
    }

    @Test
    void validationExceptionWhenUsernameContainsNumbers() {
        ResponseEntity<ResponseMessage> response = restTemplate
                .postForEntity("/api/register", new LoginCredentials("Aaa1231", "pass"), ResponseMessage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Username can contain only alphabet characters.");
    }

    @Test
    void validationExceptionWhenUsernameStartsWithLowercase() {
        ResponseEntity<ResponseMessage> response = restTemplate
                .postForEntity("/api/register", new LoginCredentials("aaaa", "pass"), ResponseMessage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Username must start with a capital character.");
    }

    @Test
    void shouldReturnOkWhenCredentialsAreProper() {
        ResponseEntity<ResponseMessage> response = restTemplate
                .postForEntity("/api/register", new LoginCredentials("Username", "pass"), ResponseMessage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Registered successfully");
    }

    @Test
    void validationExceptionWhenPasswordIsTooShort() {
        ResponseEntity<ResponseMessage> response = restTemplate
                .postForEntity("/api/register", new LoginCredentials("Username", "pas"), ResponseMessage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Password must be between 4 and 30 characters.");
    }

    @Test
    void validationExceptionWhenPasswordIsTooLong() {
        ResponseEntity<ResponseMessage> response = restTemplate
                .postForEntity("/api/register", new LoginCredentials("Username", new String(new char[31])), ResponseMessage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Password must be between 4 and 30 characters.");
    }

    private String generateProperTooLongString(int length) {
        StringBuilder stringBuilder = new StringBuilder("A");
        for (int i = 0; i < length - 1; i++) {
            stringBuilder.append("a");
        }
        return stringBuilder.toString();
    }


}
