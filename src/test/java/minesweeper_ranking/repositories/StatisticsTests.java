package minesweeper_ranking.repositories;

import minesweeper_ranking.enums.Level;
import minesweeper_ranking.models.player.Player;
import minesweeper_ranking.models.statistics.Statistics;
import minesweeper_ranking.repositories.statistics.StatisticsRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class StatisticsTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private StatisticsRepository statisticsRepository;

    private static Player player;

    @BeforeAll
    static void setUp() {
        player = new Player("Anon", "password");
    }

    @Test
    void shouldReturnCorrectData() {
        player = entityManager.persistAndFlush(player);
        entityManager.persistAndFlush(getStatistics());

        Optional<Statistics> stats = statisticsRepository.findByPlayerUsernameAndLevel("Anon", Level.EASY);

        assertThat(stats).isPresent();
        assertThat(stats.get().getPlayer()).isEqualTo(player);
        assertThat(stats.get().getLevel()).isEqualTo(Level.EASY);
        assertThat(stats.get().getTotalGamesPlayed()).isEqualTo(10);
        assertThat(stats.get().getGamesWon()).isEqualTo(5);
        assertThat(stats.get().getTotalTime()).isEqualTo(100f);
        assertThat(stats.get().getAverageTime()).isEqualTo(15f);
        assertThat(stats.get().getBestTime()).isEqualTo(10f);
    }

    private Statistics getStatistics() {
        Statistics statistics = new Statistics();
        statistics.setTotalGamesPlayed(10);
        statistics.setGamesWon(5);
        statistics.setTotalTime(100f);
        statistics.setAverageTime(15f);
        statistics.setBestTime(10f);
        statistics.setLevel(Level.EASY);
        statistics.setPlayer(player);
        return statistics;
    }

}
