package minesweeper_ranking.repositories;

import minesweeper_ranking.entities.RankingEasy;
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

        assertThat(rankingEasyRepository.countByUsername("Anon")).isEqualTo(3);
        assertThat(rankingEasyRepository.averageTime("Anon")).isEqualTo(Optional.of(15f));
    }

    private RankingEasy getRecord(float time) {
        RankingEasy rankingEasy = new RankingEasy();
        rankingEasy.setUsername("Anon");
        rankingEasy.setTime(time);
        return rankingEasy;
    }

}
