package minesweeper_ranking.models.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import minesweeper_ranking.models.player.Player;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public abstract class Statistics {

    private UUID id;

    private Player player;

    private int totalGamesPlayed;

    private int gamesWon;

    private float totalTime;

    private float averageTime;

    private float bestTime;

}
