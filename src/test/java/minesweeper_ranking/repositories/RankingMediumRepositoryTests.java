package minesweeper_ranking.repositories;

import minesweeper_ranking.models.player.Player;
import minesweeper_ranking.models.ranking.RankingMedium;
import minesweeper_ranking.repositories.ranking.RankingMediumRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RankingMediumRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RankingMediumRepository rankingMediumRepository;

    @Test
    void shouldReturnCorrectData() {
        entityManager.persist(getRecord(10f));
        entityManager.persist(getRecord(15f));
        entityManager.persist(getRecord(20f));
        entityManager.flush();

        assertThat(rankingMediumRepository.countByPlayerUsername("Anon")).isEqualTo(3);
        assertThat(rankingMediumRepository.averageTime("Anon")).isEqualTo(Optional.of(15f));
    }

    private RankingMedium getRecord(float time) {
        Player player = new Player();
        player.setUsername("Anon");
        entityManager.persistAndFlush(player);
        RankingMedium rankingMedium = new RankingMedium();
        rankingMedium.setPlayer(player);
        rankingMedium.setTime(time);
        return rankingMedium;
    }

}
