package minesweeper_ranking.dto.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StatisticsDto {

    private Integer totalGamesPlayed;

    private Integer gamesWon;

    private Float totalTime;

    private Float averageTime;

    private Float bestTime;

}
