package minesweeper_ranking.services;

import lombok.AllArgsConstructor;
import minesweeper_ranking.dto.statistics.StatisticsDto;
import minesweeper_ranking.enums.Level;
import minesweeper_ranking.models.player.Player;
import minesweeper_ranking.models.request.RequestRecord;
import minesweeper_ranking.models.response.ResponseStatistics;
import minesweeper_ranking.models.statistics.Statistics;
import minesweeper_ranking.models.statistics.StatisticsEasy;
import minesweeper_ranking.models.statistics.StatisticsHard;
import minesweeper_ranking.models.statistics.StatisticsMedium;
import minesweeper_ranking.repositories.player.PlayerRepository;
import minesweeper_ranking.repositories.statistics.StatisticsEasyRepository;
import minesweeper_ranking.repositories.statistics.StatisticsHardRepository;
import minesweeper_ranking.repositories.statistics.StatisticsMediumRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class StatisticsService {

    private final PlayerRepository playerRepository;

    private final StatisticsEasyRepository statisticsEasyRepository;
    private final StatisticsMediumRepository statisticsMediumRepository;
    private final StatisticsHardRepository statisticsHardRepository;

    public ResponseStatistics getResponseStatistics(String username) {
        Player player = getPlayerByUsername(username);
        ResponseStatistics statistics = new ResponseStatistics();

        statistics.setUsername(username);

        Statistics easyStats = getStatistics(Level.EASY, player);
        statistics.setStatisticsEasy(mapToStatisticsDto(easyStats));

        Statistics mediumStats = getStatistics(Level.MEDIUM, player);
        statistics.setStatisticsMedium(mapToStatisticsDto(mediumStats));

        Statistics hardStats = getStatistics(Level.HARD, player);
        statistics.setStatisticsHard(mapToStatisticsDto(hardStats));

        return statistics;
    }

    public void updateStatisticsWhenGameWon(Level level, Player player, RequestRecord record) {
        Statistics statistics = getStatistics(level, player);

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

        saveStatistics(level, statistics);
    }

    public void updateStatisticsWhenGameLost(Level level, String username, RequestRecord record) {
        Player player = getPlayerByUsername(username);
        Statistics statistics = getStatistics(level, player);

        statistics.setTotalGamesPlayed(statistics.getTotalGamesPlayed() + 1);
        statistics.setTotalTime(statistics.getTotalTime() + record.getTime());

        saveStatistics(level, statistics);
    }

    private void saveStatistics(Level level, Statistics statistics) {
        switch (level) {
            case EASY:
                statisticsEasyRepository.save((StatisticsEasy) statistics);
                break;
            case MEDIUM:
                statisticsMediumRepository.save((StatisticsMedium) statistics);
                break;
            case HARD:
                statisticsHardRepository.save((StatisticsHard) statistics);
                break;
        }
    }

    private Statistics getStatistics(Level level, Player player) {
        Optional<Statistics> stats = Optional.empty();
        switch (level) {
            case EASY:
                stats = statisticsEasyRepository.findByPlayer(player);
                break;
            case MEDIUM:
                stats = statisticsMediumRepository.findByPlayer(player);
                break;
            case HARD:
                stats = statisticsHardRepository.findByPlayer(player);
                break;
        }
        return stats.orElseGet(() -> createNewEmptyStatistics(level, player));
    }

    private Statistics createNewEmptyStatistics(Level level, Player player) {
        Statistics stats = null;
        switch (level) {
            case EASY:
                stats = new StatisticsEasy();
                stats.setPlayer(player);
                stats = statisticsEasyRepository.save((StatisticsEasy) stats);
                break;
            case MEDIUM:
                stats = new StatisticsMedium();
                stats.setPlayer(player);
                stats = statisticsMediumRepository.save((StatisticsMedium) stats);
                break;
            case HARD:
                stats = new StatisticsHard();
                stats.setPlayer(player);
                stats = statisticsHardRepository.save((StatisticsHard) stats);
                break;
        }
        return stats;
    }

    private StatisticsDto mapToStatisticsDto(Statistics statistics) {
        return new ModelMapper().map(statistics, StatisticsDto.class);
    }

    private Player getPlayerByUsername(String username) {
        return playerRepository.findByUsername(username).orElse(null);
    }

}
