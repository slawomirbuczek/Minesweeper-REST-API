package minesweeper_ranking.service;

import lombok.AllArgsConstructor;
import minesweeper_ranking.exceptions.UserAlreadyExistsException;
import minesweeper_ranking.model.LoginCredentials;
import minesweeper_ranking.model.ResponseMessage;
import minesweeper_ranking.model.entity.Player;
import minesweeper_ranking.repository.PlayerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RegistrationService {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseMessage addPlayer(LoginCredentials loginCredentials) {
        String username = loginCredentials.getUsername();
        String password = loginCredentials.getPassword();

        if (playerRepository.existsByUsername(username)) {
            throw new UserAlreadyExistsException(username);
        }

        Player player = new Player();
        player.setUsername(username);
        player.setPassword(passwordEncoder.encode(password));
        playerRepository.save(player);

        return new ResponseMessage("Registered successfully");
    }

}
