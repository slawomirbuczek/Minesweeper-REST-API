package minesweeper_ranking.services;

import minesweeper_ranking.authentication.UserDetailsServiceImpl;
import minesweeper_ranking.models.player.Player;
import minesweeper_ranking.repositories.player.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTests {

    @Mock
    private PlayerRepository playerRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        given(playerRepository.findByUsername(any(String.class))).willReturn(Optional.empty());

        String username = "Anon";

        assertThatThrownBy(() -> userDetailsService.loadUserByUsername(username))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("Player with username " + username + " not found");
    }

    @Test
    void shouldReturnUserDetailsWhenUserFound() {
        Player player = new Player("Anon", "password");

        given(playerRepository.findByUsername(any(String.class))).willReturn(Optional.of(player));

        UserDetails userDetails = userDetailsService.loadUserByUsername("Anon");

        assertThat(userDetails.getUsername()).isEqualTo("Anon");
        assertThat(userDetails.getPassword()).isEqualTo("password");
    }
}
