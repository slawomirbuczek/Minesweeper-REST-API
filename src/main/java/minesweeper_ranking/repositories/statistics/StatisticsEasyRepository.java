package minesweeper_ranking.repositories.statistics;

import minesweeper_ranking.models.player.Player;
import minesweeper_ranking.models.statistics.Statistics;
import minesweeper_ranking.models.statistics.StatisticsEasy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatisticsEasyRepository extends JpaRepository<StatisticsEasy, UUID> {

    Optional<Statistics> findByPlayer(Player player);


}
