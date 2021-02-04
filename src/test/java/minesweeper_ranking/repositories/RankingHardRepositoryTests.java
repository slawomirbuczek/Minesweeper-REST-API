package minesweeper_ranking.repositories;

import minesweeper_ranking.models.player.Player;
import minesweeper_ranking.models.ranking.RankingHard;
import minesweeper_ranking.repositories.ranking.RankingHardRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RankingHardRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RankingHardRepository rankingHardRepository;

    @Test
    void shouldReturnCorrectData() {
        entityManager.persist(getRecord(10f));
        entityManager.persist(getRecord(15f));
        entityManager.persist(getRecord(20f));
        entityManager.flush();

        assertThat(rankingHardRepository.countByPlayerUsername("Anon")).isEqualTo(3);
        assertThat(rankingHardRepository.averageTime("Anon")).isEqualTo(Optional.of(15f));
    }

    private RankingHard getRecord(float time) {
        Player player = new Player();
        player.setUsername("Anon");
        entityManager.persistAndFlush(player);
        RankingHard rankingHard = new RankingHard();
        rankingHard.setPlayer(player);
        rankingHard.setTime(time);
        return rankingHard;
    }

}
