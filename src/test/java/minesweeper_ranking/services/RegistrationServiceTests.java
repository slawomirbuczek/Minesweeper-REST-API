package minesweeper_ranking.services;

import minesweeper_ranking.models.request.RequestCredentials;
import minesweeper_ranking.exceptions.UserAlreadyExistsException;
import minesweeper_ranking.models.response.ResponseMessage;
import minesweeper_ranking.repositories.player.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTests {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    void shouldReturnMessageWhenUserCreated() {
        given(playerRepository.existsByUsername(any(String.class))).willReturn(false);

        RequestCredentials requestCredentials = new RequestCredentials("Anon", "password");

        ResponseMessage message = registrationService.addPlayer(requestCredentials);

        assertThat(message.getMessage()).isEqualTo("Registered successfully");
    }

    @Test
    void shouldThrowExceptionWheUsernameIsTaken() {
        given(playerRepository.existsByUsername(any(String.class))).willReturn(true);

        RequestCredentials requestCredentials = new RequestCredentials("Anon", "password");

        assertThatThrownBy(() -> registrationService.addPlayer(requestCredentials))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("Player with username " + requestCredentials.getUsername() + " already exists");
    }

}
