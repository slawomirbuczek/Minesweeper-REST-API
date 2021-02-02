package minesweeper_ranking.repository;

import minesweeper_ranking.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {

    Optional<Player> findPlayerByUsername(String username);

    boolean existsByUsername(String username);

}
