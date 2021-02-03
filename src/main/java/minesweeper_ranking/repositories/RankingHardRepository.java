package minesweeper_ranking.repositories;

import minesweeper_ranking.entities.RankingHard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RankingHardRepository extends JpaRepository<RankingHard, UUID> {

    int countByUsername (String username);

    @Query("SELECT AVG(ranking.time) FROM RankingHard ranking WHERE ranking.username = ?1")
    Optional<Float> averageTime(String username);

}
