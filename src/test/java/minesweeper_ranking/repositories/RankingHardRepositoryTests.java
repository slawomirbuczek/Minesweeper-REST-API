package minesweeper_ranking.repositories;

import minesweeper_ranking.entities.RankingHard;
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

        assertThat(rankingHardRepository.countByUsername("Anon")).isEqualTo(3);
        assertThat(rankingHardRepository.averageTime("Anon")).isEqualTo(Optional.of(15f));
    }

    private RankingHard getRecord(float time) {
        RankingHard rankingHard = new RankingHard();
        rankingHard.setUsername("Anon");
        rankingHard.setTime(time);
        return rankingHard;
    }

}
