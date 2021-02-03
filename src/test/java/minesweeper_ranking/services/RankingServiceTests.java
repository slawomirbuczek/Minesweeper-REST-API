package minesweeper_ranking.services;

import minesweeper_ranking.enums.Level;
import minesweeper_ranking.entities.RankingEasy;
import minesweeper_ranking.models.RequestRecord;
import minesweeper_ranking.repositories.RankingEasyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RankingServiceTests {

    @Mock
    private RankingEasyRepository rankingEasyRepository;

    @InjectMocks
    private RankingService rankingService;

    @Test
    void shouldAlwaysReturnMessageWhenAddingRecord() {
        RequestRecord record = new RequestRecord(12, new Date());
        Principal principal = () -> "Anon";

        given(rankingEasyRepository.save(any(RankingEasy.class))).
                willAnswer(i -> i.getArgument(0, RankingEasy.class));

        assertThat(rankingService.addRecord(record, Level.EASY, principal).getMessage())
                .isEqualTo("Record added!");
    }

    @Test
    void shouldReturnListOfRankingDtoWhenGettingRanking() {
        Page<RankingEasy> page = new PageImpl<>(getRankingEasyList());

        given(rankingEasyRepository.findAll(any(Pageable.class))).willReturn(page);

        assertThat(rankingService.getRanking(Level.EASY)).hasSize(4);
        assertThat(rankingService.getRanking(Level.EASY).get(0).getUsername())
                .isEqualTo("Anon");
        assertThat(rankingService.getRanking(Level.EASY).get(1).getTime())
                .isEqualTo(12);
        assertThat(rankingService.getRanking(Level.EASY).get(2).getTime())
                .isEqualTo(13);
        assertThat(rankingService.getRanking(Level.EASY).get(3).getTime())
                .isEqualTo(14);

    }

    private List<RankingEasy> getRankingEasyList() {
        List<RankingEasy> list = new ArrayList<>();
        list.add(new RankingEasy(UUID.randomUUID(), new Date(), 10, "Anon"));
        list.add(new RankingEasy(UUID.randomUUID(), new Date(), 12, "Anon"));
        list.add(new RankingEasy(UUID.randomUUID(), new Date(), 13, "Anon"));
        list.add(new RankingEasy(UUID.randomUUID(), new Date(), 14, "Anon"));
        return list;
    }

}
