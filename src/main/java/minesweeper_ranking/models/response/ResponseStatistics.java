package minesweeper_ranking.models.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import minesweeper_ranking.dto.statistics.StatisticsDto;

@Getter
@Setter
@NoArgsConstructor
public class ResponseStatistics {

    private String username;

    private StatisticsDto statisticsEasy;

    private StatisticsDto statisticsMedium;

    private StatisticsDto statisticsHard;

}
