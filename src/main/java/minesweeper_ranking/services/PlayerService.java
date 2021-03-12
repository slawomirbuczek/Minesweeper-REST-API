package minesweeper_ranking.services;

import lombok.AllArgsConstructor;
import minesweeper_ranking.models.request.RequestCredentials;
import minesweeper_ranking.models.player.Player;
import minesweeper_ranking.exceptions.UserAlreadyExistsException;
import minesweeper_ranking.models.response.ResponseMessage;
import minesweeper_ranking.repositories.player.PlayerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseMessage addPlayer(RequestCredentials requestCredentials) {
        String username = requestCredentials.getUsername();

        if (playerRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException(username);
        }

        requestCredentials.setPassword(passwordEncoder.encode(requestCredentials.getPassword()));

        Player player = new ModelMapper().map(requestCredentials, Player.class);
        playerRepository.save(player);

        return new ResponseMessage("Registered successfully");
    }

    public Player getPlayer(String username) {
        return playerRepository.findByUsername(username).orElse(null);
    }

}
