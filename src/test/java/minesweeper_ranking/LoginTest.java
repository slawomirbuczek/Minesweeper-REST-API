package minesweeper_ranking;

import minesweeper_ranking.model.LoginCredentials;
import minesweeper_ranking.model.ResponseMessage;
import minesweeper_ranking.model.entity.Player;
import minesweeper_ranking.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LoginTest {

    @MockBean
    private PlayerRepository playerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void canLoginWhenExists() {
        Player player = new Player(UUID.randomUUID(), "player", passwordEncoder.encode("pass"));
        BDDMockito.given(playerRepository.findPlayerByUsername("player"))
                .willReturn(Optional.of(player));

        ResponseEntity<ResponseMessage> response = restTemplate
                .postForEntity("/api/login", new LoginCredentials("player", "pass"), ResponseMessage.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Logged in successfully");
        assertThat(response.getHeaders().get("Authorization")).isNotEmpty();
    }
}
