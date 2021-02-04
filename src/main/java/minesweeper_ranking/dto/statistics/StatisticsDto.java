package minesweeper_ranking.dto.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StatisticsDto {

    private int totalGamesPlayed;

    private int gamesWon;

    private float totalTime;

    private float averageTime;

    private float bestTime;

}
