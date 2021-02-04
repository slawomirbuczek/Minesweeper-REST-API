package minesweeper_ranking.repositories;

import minesweeper_ranking.models.player.Player;
import minesweeper_ranking.models.ranking.RankingEasy;
import minesweeper_ranking.repositories.player.PlayerRepository;
import minesweeper_ranking.repositories.ranking.RankingEasyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RankingEasyRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RankingEasyRepository rankingEasyRepository;

    @Test
    void shouldReturnCorrectData() {
        entityManager.persist(getRecord(10f));
        entityManager.persist(getRecord(15f));
        entityManager.persist(getRecord(20f));
        entityManager.flush();

        assertThat(rankingEasyRepository.countByPlayerUsername("Anon")).isEqualTo(3);
        assertThat(rankingEasyRepository.averageTime("Anon")).isEqualTo(Optional.of(15f));
    }

    private RankingEasy getRecord(float time) {
        Player player = new Player();
        player.setUsername("Anon");
        entityManager.persistAndFlush(player);
        RankingEasy rankingEasy = new RankingEasy();
        rankingEasy.setPlayer(player);
        rankingEasy.setTime(time);
        return rankingEasy;
    }

}
