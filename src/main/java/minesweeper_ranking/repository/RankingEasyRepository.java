package minesweeper_ranking.repository;

import minesweeper_ranking.model.entity.RankingEasy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RankingEasyRepository extends JpaRepository<RankingEasy, UUID> {

}
