package minesweeper_ranking.repository;

import minesweeper_ranking.model.RankingMedium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RankingMediumRepository extends JpaRepository<RankingMedium, UUID> {

}