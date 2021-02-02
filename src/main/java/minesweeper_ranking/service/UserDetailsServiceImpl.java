package minesweeper_ranking.service;

import lombok.AllArgsConstructor;
import minesweeper_ranking.model.Player;
import minesweeper_ranking.repository.PlayerRepository;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Player> optionalPlayer = playerRepository.findPlayerByUsername(username);

        return optionalPlayer.orElseThrow(() -> new UsernameNotFoundException(
                "Player with username " + username + " not found"
        ));
    }

}
