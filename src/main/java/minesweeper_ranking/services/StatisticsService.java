package minesweeper_ranking.services;

import lombok.AllArgsConstructor;
import minesweeper_ranking.dto.statistics.StatisticsDto;
import minesweeper_ranking.enums.Level;
import minesweeper_ranking.models.player.Player;
import minesweeper_ranking.models.ranking.RequestRecord;
import minesweeper_ranking.models.statistics.ResponseStatistics;
import minesweeper_ranking.models.statistics.Statistics;
import minesweeper_ranking.repositories.statistics.StatisticsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class StatisticsService {

    private final PlayerService playerService;
    private final StatisticsRepository statisticsRepository;
    private final ModelMapper mapper;

    public ResponseStatistics getStatistics(String username) {
        ResponseStatistics statistics = new ResponseStatistics();
        statistics.setUsername(username);

        Statistics easyStats = getStatistics(username, Level.EASY);
        statistics.setEasy(mapToStatisticsDto(easyStats));

        Statistics mediumStats = getStatistics(username, Level.MEDIUM);
        statistics.setMedium(mapToStatisticsDto(mediumStats));

        Statistics hardStats = getStatistics(username, Level.HARD);
        statistics.setHard(mapToStatisticsDto(hardStats));

        return statistics;
    }

    public void updateStatisticsWhenGameWon(Level level, String username, RequestRecord record) {
        Statistics statistics = getStatistics(username, level);

        statistics.setTotalGamesPlayed(statistics.getTotalGamesPlayed() + 1);
        statistics.setGamesWon(statistics.getGamesWon() + 1);
        statistics.setTotalTime(statistics.getTotalTime() + record.getTime());
        statistics.setAverageTime(
                ((statistics.getAverageTime() * (statistics.getGamesWon() - 1)) + record.getTime())
                        / statistics.getGamesWon()
        );
        statistics.setBestTime(
                statistics.getBestTime() == 0 ? record.getTime() :
                        Math.min(statistics.getBestTime(), record.getTime())
        );

        statisticsRepository.save(statistics);
    }

    public void updateStatisticsWhenGameLost(Level level, String username, RequestRecord record) {
        Player player = playerService.getPlayer(username);
        Statistics statistics = getStatistics(username, level);

        statistics.setTotalGamesPlayed(statistics.getTotalGamesPlayed() + 1);
        statistics.setTotalTime(statistics.getTotalTime() + record.getTime());

        statisticsRepository.save(statistics);
    }

    private Statistics getStatistics(String username, Level level) {
        Optional<Statistics> statistics = statisticsRepository.findByPlayerUsernameAndLevel(username, level);
        return statistics.orElseGet(() -> createNewEmptyStatistics(username, level));
    }

    private Statistics createNewEmptyStatistics(String username, Level level) {
        Statistics statistics = new Statistics();
        statistics.setPlayer(playerService.getPlayer(username));
        statistics.setLevel(level);
        return statistics;
    }

    private StatisticsDto mapToStatisticsDto(Statistics statistics) {
        return mapper.map(statistics, StatisticsDto.class);
    }

}
