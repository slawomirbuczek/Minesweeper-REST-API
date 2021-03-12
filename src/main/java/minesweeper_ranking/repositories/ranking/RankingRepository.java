package minesweeper_ranking.repositories.ranking;

import minesweeper_ranking.enums.Level;
import minesweeper_ranking.models.ranking.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RankingRepository extends JpaRepository<Ranking, Long> {

    int countByPlayerUsername(String username);

    @Query("SELECT AVG(time) FROM Ranking WHERE player.username = ?1 AND level = ?2")
    Optional<Float> averageTime(String username, Level level);

}
