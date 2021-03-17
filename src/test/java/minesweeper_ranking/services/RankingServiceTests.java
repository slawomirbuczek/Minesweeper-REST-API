package minesweeper_ranking.services;


import minesweeper_ranking.dto.ranking.RecordDto;
import minesweeper_ranking.enums.Level;
import minesweeper_ranking.models.player.Player;
import minesweeper_ranking.models.ranking.Ranking;
import minesweeper_ranking.models.ranking.RequestRecord;
import minesweeper_ranking.repositories.ranking.RankingRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class RankingServiceTests {

    @Mock
    private PlayerService playerService;

    @Mock
    private RankingRepository rankingRepository;

    @Mock
    private StatisticsService statisticsService;

    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private RankingService rankingService;

    private static Player player;

    @BeforeAll
    static void setUp() {
        player = new Player("Anon", "password");
    }

    @Test
    void shouldAddRecordAndReturnDto() {
        given(playerService.getPlayer("Anon")).willReturn(player);
        given(rankingRepository.save(any())).willAnswer(i -> i.getArgument(0));

        RequestRecord record = new RequestRecord(10f, LocalDate.of(2020, 2, 2));
        RecordDto recordDto = rankingService.addRecord(record, Level.EASY, "Anon");

        assertThat(recordDto.getUsername()).isEqualTo("Anon");
        assertThat(recordDto.getDate()).isEqualTo(LocalDate.of(2020, 2, 2));
        assertThat(recordDto.getTime()).isEqualTo(10f);
    }

    @Test
    void shouldReturnRanking() {
        given(rankingRepository.findAllByLevel(any(), any())).willReturn(new PageImpl<>(getListOfRanking()));

        List<RecordDto> ranking = rankingService.getRanking(Level.EASY);

        assertThat(ranking.size()).isEqualTo(4);
        assertThat(ranking.get(0).getTime()).isEqualTo(10f);
        assertThat(ranking.get(3).getTime()).isEqualTo(13f);
    }

    private List<Ranking> getListOfRanking() {
        List<Ranking> rankings = new ArrayList<>();
        rankings.add(new Ranking(1L, LocalDate.now(), 10f, Level.EASY, player));
        rankings.add(new Ranking(2L, LocalDate.now(), 11f, Level.EASY, player));
        rankings.add(new Ranking(3L, LocalDate.now(), 12f, Level.EASY, player));
        rankings.add(new Ranking(4L, LocalDate.now(), 13f, Level.EASY, player));
        return rankings;
    }


}
