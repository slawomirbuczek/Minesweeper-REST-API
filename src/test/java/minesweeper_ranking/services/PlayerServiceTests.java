package minesweeper_ranking.services;

import minesweeper_ranking.exceptions.PlayerAlreadyExistsException;
import minesweeper_ranking.exceptions.PlayerNotFoundException;
import minesweeper_ranking.models.ResponseMessage;
import minesweeper_ranking.models.player.Player;
import minesweeper_ranking.models.player.RequestCredentials;
import minesweeper_ranking.repositories.player.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTests {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PlayerService playerService;

    @Test
    void shouldReturnMessageWhenUserCreated() {
        given(playerRepository.existsByUsername(any())).willReturn(false);

        RequestCredentials requestCredentials = new RequestCredentials("Anon", "password");

        ResponseMessage message = playerService.addPlayer(requestCredentials);

        assertThat(message.getMessage()).isEqualTo("Registered successfully");
    }

    @Test
    void shouldThrowExceptionWheUsernameIsTaken() {
        given(playerRepository.existsByUsername(any())).willReturn(true);

        RequestCredentials requestCredentials = new RequestCredentials("Anon", "password");

        assertThatThrownBy(() -> playerService.addPlayer(requestCredentials))
                .isInstanceOf(PlayerAlreadyExistsException.class)
                .hasMessage("Player with username " + requestCredentials.getUsername() + " already exists");
    }

    @Test
    void shouldGetPlayer() {
        Player player = new Player("Anon", "password");
        given(playerRepository.findByUsername("Anon")).willReturn(Optional.of(player));

        assertThat(playerService.getPlayer("Anon")).isEqualTo(player);
    }

    @Test
    void shouldThrowPlayerNotFoundException() {
        given(playerRepository.findByUsername("Anon")).willReturn(Optional.empty());

        assertThatThrownBy(() -> playerService.getPlayer("Anon"))
                .isInstanceOf(PlayerNotFoundException.class)
                .hasMessage("Player with username Anon not found");

    }

}
