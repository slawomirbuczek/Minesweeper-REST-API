package minesweeper_ranking;

import minesweeper_ranking.model.LoginCredentials;
import minesweeper_ranking.model.ResponseMessage;
import minesweeper_ranking.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RegistrationTest {

    @Mock
    private PlayerRepository playerRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void cantRegisterWhenUsernameIsAlreadyTaken() {
        BDDMockito.given(playerRepository.existsByUsername("Username"))
                .willReturn(true);

        ResponseEntity<ResponseMessage> response = restTemplate
                .postForEntity("/api/register", new LoginCredentials("Username", "pass"), ResponseMessage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Player with username username already exists");
    }

    @Test
    void canRegisterWhenUsernameIsFree() {
        BDDMockito.given(playerRepository.existsByUsername("Username"))
                .willReturn(false);

        ResponseEntity<ResponseMessage> response = restTemplate
                .postForEntity("/api/register", new LoginCredentials("Username", "pass"), ResponseMessage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Registered successfully");
    }

}
