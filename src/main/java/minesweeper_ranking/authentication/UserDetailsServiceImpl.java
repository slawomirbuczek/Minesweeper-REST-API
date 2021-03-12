package minesweeper_ranking.authentication;

import lombok.AllArgsConstructor;
import minesweeper_ranking.models.player.Player;
import minesweeper_ranking.repositories.player.PlayerRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final PlayerRepository playerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {

        Optional<Player> optionalPlayer = playerRepository.findByUsername(username);

        return optionalPlayer.orElseThrow(() -> new UsernameNotFoundException(
                "Player with username " + username + " not found"
        ));
    }

}
