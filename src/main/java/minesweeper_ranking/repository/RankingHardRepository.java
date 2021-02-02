package minesweeper_ranking.repository;

import minesweeper_ranking.model.RankingHard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RankingHardRepository extends JpaRepository<RankingHard, UUID> {

}