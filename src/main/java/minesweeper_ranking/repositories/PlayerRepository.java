package minesweeper_ranking.repositories;

import minesweeper_ranking.entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlayerRepository extends JpaRepository<Player, UUID> {

    Optional<Player> findPlayerByUsername(String username);

    boolean existsByUsername(String username);

}
