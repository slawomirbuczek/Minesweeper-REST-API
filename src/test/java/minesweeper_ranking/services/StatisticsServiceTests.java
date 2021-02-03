package minesweeper_ranking.services;

import minesweeper_ranking.models.Statistics;
import minesweeper_ranking.repositories.RankingEasyRepository;
import minesweeper_ranking.repositories.RankingHardRepository;
import minesweeper_ranking.repositories.RankingMediumRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class StatisticsServiceTests {

    @Mock
    private RankingEasyRepository rankingEasyRepository;

    @Mock
    private RankingMediumRepository rankingMediumRepository;

    @Mock
    private RankingHardRepository rankingHardRepository;

    @InjectMocks
    private StatisticsService statisticsService;

    @Test
    void shouldReturnStatisticsWhenThereIsSomeData() {
        given(rankingEasyRepository.countByUsername("Anon")).willReturn(10);
        given(rankingMediumRepository.countByUsername("Anon")).willReturn(15);
        given(rankingHardRepository.countByUsername("Anon")).willReturn(20);

        given(rankingEasyRepository.averageTime("Anon")).willReturn(Optional.of(10.5f));
        given(rankingMediumRepository.averageTime("Anon")).willReturn(Optional.of(15.5f));
        given(rankingHardRepository.averageTime("Anon")).willReturn(Optional.of(20.5f));

        Statistics statistics = statisticsService.getStatistics("Anon");

        assertThat(statistics.getEasyGamesWon()).isEqualTo(10);
        assertThat(statistics.getMediumGamesWon()).isEqualTo(15);
        assertThat(statistics.getHardGamesWon()).isEqualTo(20);

        assertThat(statistics.getAverageEasyGameTime()).isEqualTo(10.5f);
        assertThat(statistics.getAverageMediumGameTime()).isEqualTo(15.5f);
        assertThat(statistics.getAverageHardGameTime()).isEqualTo(20.5f);
    }

    @Test
    void shouldReturnZeroEverywhereWhenThereIsNoData() {
        given(rankingEasyRepository.countByUsername("Anon")).willReturn(0);
        given(rankingMediumRepository.countByUsername("Anon")).willReturn(0);
        given(rankingHardRepository.countByUsername("Anon")).willReturn(0);

        given(rankingEasyRepository.averageTime("Anon")).willReturn(Optional.empty());
        given(rankingMediumRepository.averageTime("Anon")).willReturn(Optional.empty());
        given(rankingHardRepository.averageTime("Anon")).willReturn(Optional.empty());

        Statistics statistics = statisticsService.getStatistics("Anon");

        assertThat(statistics.getEasyGamesWon()).isEqualTo(0);
        assertThat(statistics.getMediumGamesWon()).isEqualTo(0);
        assertThat(statistics.getHardGamesWon()).isEqualTo(0);

        assertThat(statistics.getAverageEasyGameTime()).isEqualTo(0);
        assertThat(statistics.getAverageMediumGameTime()).isEqualTo(0);
        assertThat(statistics.getAverageHardGameTime()).isEqualTo(0);
    }

}
