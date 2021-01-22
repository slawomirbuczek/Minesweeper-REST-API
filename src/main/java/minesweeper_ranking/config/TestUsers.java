package minesweeper_ranking.config;

import minesweeper_ranking.model.entity.Player;
import minesweeper_ranking.repository.PlayerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TestUsers {
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;

    public TestUsers(PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    private void addTestPlayers() {
        Player jonSnow = new Player();
        jonSnow.setUsername("Jon");
        jonSnow.setPassword(passwordEncoder.encode("Snow"));
        playerRepository.save(jonSnow);

        Player littleFinger = new Player();
        littleFinger.setUsername("Little");
        littleFinger.setPassword(passwordEncoder.encode("Finger"));
        playerRepository.save(littleFinger);

        Player string = new Player();
        string.setUsername("string");
        string.setPassword(passwordEncoder.encode("string"));
        playerRepository.save(string);
    }
}
