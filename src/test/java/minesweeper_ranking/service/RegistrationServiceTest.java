package minesweeper_ranking.service;

import minesweeper_ranking.exceptions.UserAlreadyExistsException;
import minesweeper_ranking.model.LoginCredentials;
import minesweeper_ranking.model.ResponseMessage;
import minesweeper_ranking.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    void shouldReturnMessageWhenUsernameIsAvailable() {
        given(playerRepository.existsByUsername("User")).willReturn(false);

        ResponseMessage message = registrationService.addPlayer(new LoginCredentials("User", "password"));

        assertThat(message.getMessage()).isEqualTo("Registered successfully");
    }

    @Test
    void shouldThrowExceptionWhenUsernameAlreadyExists() {
        given(playerRepository.existsByUsername("User")).willReturn(true);

        assertThatExceptionOfType(UserAlreadyExistsException.class)
                .isThrownBy(() -> registrationService.addPlayer(new LoginCredentials("User", "password")))
        .withMessage("Player with username User already exists");
    }

}
