package minesweeper_ranking.service;

import minesweeper_ranking.model.entity.Player;
import minesweeper_ranking.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void shouldReturnPlayerWhenPlayerExists() {
        Player player = new Player(UUID.randomUUID(), "Player", "password");
        given(playerRepository.findPlayerByUsername("Player")).willReturn(Optional.of(player));

        UserDetails userDetails = userDetailsService.loadUserByUsername("Player");

        assertThat(userDetails).isEqualTo(player);
    }

    @Test
    void shouldThrowExceptionWhenPlayerNotFound() {
        given(playerRepository.findPlayerByUsername("Player")).willReturn(Optional.empty());

        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> userDetailsService.loadUserByUsername("Player"));
    }

}
