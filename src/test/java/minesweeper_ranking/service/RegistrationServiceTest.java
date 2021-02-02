package minesweeper_ranking.service;

import minesweeper_ranking.dto.PlayerDto;
import minesweeper_ranking.exceptions.UserAlreadyExistsException;
import minesweeper_ranking.model.ResponseMessage;
import minesweeper_ranking.repository.PlayerRepository;
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
class RegistrationServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private RegistrationService registrationService;

    @Test
    void shouldReturnMessageWhenUserCreated() {
        given(playerRepository.existsByUsername(any(String.class))).willReturn(false);

        PlayerDto playerDto = new PlayerDto("Anon", "password");

        ResponseMessage message = registrationService.addPlayer(playerDto);

        assertThat(message.getMessage()).isEqualTo("Registered successfully");
    }

    @Test
    void shouldThrowExceptionWheUsernameIsTaken() {
        given(playerRepository.existsByUsername(any(String.class))).willReturn(true);

        PlayerDto playerDto = new PlayerDto("Anon", "password");

        assertThatThrownBy(() -> registrationService.addPlayer(playerDto))
                .isInstanceOf(UserAlreadyExistsException.class)
                .hasMessage("Player with username " + playerDto.getUsername() + " already exists");
    }

}
