package minesweeper_ranking.repositories.statistics;

import minesweeper_ranking.models.player.Player;
import minesweeper_ranking.models.statistics.Statistics;
import minesweeper_ranking.models.statistics.StatisticsHard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatisticsHardRepository extends JpaRepository<StatisticsHard, UUID> {

    Optional<Statistics> findByPlayer(Player player);


}
