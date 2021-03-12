package minesweeper_ranking.models.statistics;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import minesweeper_ranking.dto.statistics.StatisticsDto;

@Getter
@Setter
@NoArgsConstructor
public class ResponseStatistics {

    private String username;

    private StatisticsDto easy;

    private StatisticsDto medium;

    private StatisticsDto hard;

}
