package minesweeper_ranking.repositories.ranking;

import minesweeper_ranking.models.ranking.RankingMedium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RankingMediumRepository extends JpaRepository<RankingMedium, UUID> {

    int countByPlayerUsername(String username);

    @Query("SELECT AVG(ranking.time) FROM RankingMedium ranking WHERE ranking.player.username = ?1")
    Optional<Float> averageTime(String username);

}
