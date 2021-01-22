package minesweeper_ranking.service;

import minesweeper_ranking.model.entity.Player;
import minesweeper_ranking.model.RegistrationRequest;
import minesweeper_ranking.model.ResponseMessage;
import minesweeper_ranking.repository.PlayerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RegistrationService {

    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseMessage addPlayer(RegistrationRequest registrationRequest) {
        ifUsernameAlreadyExist(registrationRequest.getUsername());

        Player player = new Player();
        player.setUsername(registrationRequest.getUsername());
        player.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        playerRepository.save(player);

        return new ResponseMessage("Registered successfully!");
    }

    public void ifUsernameAlreadyExist(String username) {
        if (playerRepository.existsByUsername(username)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Player with username " + username + " already exists."
            );
        }
    }

}
