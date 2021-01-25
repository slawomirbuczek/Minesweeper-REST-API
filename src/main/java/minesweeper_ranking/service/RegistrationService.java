package minesweeper_ranking.service;

import minesweeper_ranking.exceptions.UserAlreadyExistsException;
import minesweeper_ranking.model.RegistrationRequest;
import minesweeper_ranking.model.ResponseMessage;
import minesweeper_ranking.model.entity.Player;
import minesweeper_ranking.repository.PlayerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseMessage addPlayer(RegistrationRequest registrationRequest) {
        String username = registrationRequest.getUsername();
        String password = registrationRequest.getPassword();

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
