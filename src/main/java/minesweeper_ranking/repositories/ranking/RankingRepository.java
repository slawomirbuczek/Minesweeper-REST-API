package minesweeper_ranking.repositories.ranking;

import minesweeper_ranking.enums.Level;
import minesweeper_ranking.models.ranking.Ranking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankingRepository extends JpaRepository<Ranking, Long> {

    Page<Ranking> findAllByLevel(Level level, Pageable pageable);

}
