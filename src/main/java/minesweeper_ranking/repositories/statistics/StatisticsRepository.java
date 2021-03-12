package minesweeper_ranking.repositories.statistics;

import minesweeper_ranking.enums.Level;
import minesweeper_ranking.models.statistics.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StatisticsRepository extends JpaRepository<Statistics, Long> {

    Optional<Statistics> findByPlayerUsernameAndLevel(String username, Level level);

}
