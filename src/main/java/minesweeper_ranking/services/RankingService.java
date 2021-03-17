package minesweeper_ranking.services;

import lombok.AllArgsConstructor;
import minesweeper_ranking.dto.ranking.RecordDto;
import minesweeper_ranking.enums.Level;
import minesweeper_ranking.models.player.Player;
import minesweeper_ranking.models.ranking.Ranking;
import minesweeper_ranking.models.ranking.RequestRecord;
import minesweeper_ranking.repositories.ranking.RankingRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RankingService {

    private final PlayerService playerService;
    private final RankingRepository rankingRepository;
    private final StatisticsService statisticsService;
    private final ModelMapper mapper;

    public List<RecordDto> getRanking(Level level) {
        Sort sort = Sort.by(Sort.Direction.ASC, "time");
        Pageable pageable = PageRequest.of(0, 50, sort);

        List<Ranking> ranking = rankingRepository.findAllByLevel(level, pageable).getContent();

        return ranking.stream().map(this::mapToRankingDto).collect(Collectors.toList());
    }

    public RecordDto addRecord(RequestRecord record, Level level, String username) {
        Player player = playerService.getPlayer(username);

        Ranking ranking = mapper.map(record, Ranking.class);
        ranking.setPlayer(player);
        ranking.setLevel(level);
        ranking = rankingRepository.save(ranking);

        statisticsService.updateStatisticsWhenGameWon(level, username, record);
        return mapToRankingDto(ranking);
    }

    private RecordDto mapToRankingDto(Ranking ranking) {
        RecordDto recordDto = mapper.map(ranking, RecordDto.class);
        recordDto.setUsername(ranking.getPlayer().getUsername());
        return recordDto;
    }

}
