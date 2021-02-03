package minesweeper_ranking.repositories;

import minesweeper_ranking.entities.Player;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PlayerRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void shouldReturnAndFindPlayerWhenPlayerExists() {
        Player player = new Player();
        player.setUsername("Anon");
        player.setPassword("password");

        entityManager.persistAndFlush(player);

        assertThat(playerRepository.existsByUsername("Anon")).isEqualTo(true);
        assertThat(playerRepository.findByUsername("Anon")).isEqualTo(Optional.of(player));
    }

    @Test
    void shouldNotReturnAndNotFindPlayerWhenPlayerDoesNotExist() {
        assertThat(playerRepository.existsByUsername("Anon")).isEqualTo(false);
        assertThat(playerRepository.findByUsername("Anon")).isEqualTo(Optional.empty());
    }

}
