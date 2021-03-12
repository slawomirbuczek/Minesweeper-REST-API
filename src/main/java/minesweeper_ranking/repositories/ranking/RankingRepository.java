package minesweeper_ranking.repositories.ranking;

import minesweeper_ranking.models.ranking.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankingRepository extends JpaRepository<Ranking, Long> {

}
