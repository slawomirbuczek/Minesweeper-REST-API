package minesweeper_ranking.repositories;

import minesweeper_ranking.entities.RankingEasy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RankingEasyRepository extends JpaRepository<RankingEasy, UUID> {

     int countByUsername (String username);

     @Query("SELECT AVG(ranking.time) FROM RankingEasy ranking WHERE ranking.username = ?1")
     Optional<Float> averageTime(String username);

}
