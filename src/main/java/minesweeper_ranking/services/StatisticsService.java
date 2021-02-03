package minesweeper_ranking.services;

import lombok.AllArgsConstructor;
import minesweeper_ranking.models.Statistics;
import minesweeper_ranking.repositories.RankingEasyRepository;
import minesweeper_ranking.repositories.RankingHardRepository;
import minesweeper_ranking.repositories.RankingMediumRepository;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class StatisticsService {

    private final RankingEasyRepository rankingEasyRepository;
    private final RankingMediumRepository rankingMediumRepository;
    private final RankingHardRepository rankingHardRepository;


    public Statistics getStatistics(String username) {
        Statistics statistics = new Statistics();
        statistics.setEasyGamesWon(getNumberOfEasyGamesWon(username));
        statistics.setMediumGamesWon(getNumberOfMediumGamesWon(username));
        statistics.setHardGamesWon(getNumberOfHardGamesWon(username));
        statistics.setAverageEasyGameTime(getAverageTimeOfEasyGames(username));
        statistics.setAverageMediumGameTime(getAverageTimeOfMediumGames(username));
        statistics.setAverageHardGameTime(getAverageTimeOfHardGames(username));
        return statistics;
    }

    private int getNumberOfEasyGamesWon(String username) {
        return rankingEasyRepository.countByUsername(username);
    }

    private int getNumberOfMediumGamesWon(String username) {
        return rankingMediumRepository.countByUsername(username);
    }

    private int getNumberOfHardGamesWon(String username) {
        return rankingHardRepository.countByUsername(username);
    }

    private float getAverageTimeOfEasyGames(String username) {
        return rankingEasyRepository.averageTime(username).orElse(0f);
    }

    private float getAverageTimeOfMediumGames(String username) {
        return rankingMediumRepository.averageTime(username).orElse(0f);
    }

    private float getAverageTimeOfHardGames(String username) {
        return rankingHardRepository.averageTime(username).orElse(0f);
    }

}
