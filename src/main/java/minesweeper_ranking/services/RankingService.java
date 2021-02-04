package minesweeper_ranking.services;

import lombok.AllArgsConstructor;
import minesweeper_ranking.dto.ranking.RecordDto;
import minesweeper_ranking.models.player.Player;
import minesweeper_ranking.models.ranking.RankingEasy;
import minesweeper_ranking.models.ranking.RankingHard;
import minesweeper_ranking.models.ranking.RankingMedium;
import minesweeper_ranking.enums.Level;
import minesweeper_ranking.models.ranking.Ranking;
import minesweeper_ranking.models.request.RequestRecord;
import minesweeper_ranking.repositories.player.PlayerRepository;
import minesweeper_ranking.repositories.ranking.RankingEasyRepository;
import minesweeper_ranking.repositories.ranking.RankingHardRepository;
import minesweeper_ranking.repositories.ranking.RankingMediumRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class RankingService {

    private final PlayerRepository playerRepository;
    private final RankingEasyRepository rankingEasyRepository;
    private final RankingMediumRepository rankingMediumRepository;
    private final RankingHardRepository rankingHardRepository;
    private final StatisticsService statisticsService;

    public List<RecordDto> getRanking(Level level) {
        Sort sort = Sort.by(Sort.Direction.ASC, "time");
        Pageable pageable = PageRequest.of(0, 50, sort);

        List<? extends Ranking> ranking = new ArrayList<>();

        switch (level) {
            case EASY:
                ranking = rankingEasyRepository.findAll(pageable).getContent();
                break;
            case MEDIUM:
                ranking = rankingMediumRepository.findAll(pageable).getContent();
                break;
            case HARD:
                ranking = rankingHardRepository.findAll(pageable).getContent();
                break;
        }

        return ranking.stream().map(this::mapToRankingDto).collect(Collectors.toList());
    }

    public RecordDto addRecord(RequestRecord record, Level level, String username) {

        ModelMapper modelMapper = new ModelMapper();
        Player player = getPlayer(username);

        Ranking ranking = null;
        switch (level) {
            case EASY:
                RankingEasy rankingEasy = modelMapper.map(record, RankingEasy.class);
                rankingEasy.setPlayer(player);
                ranking = rankingEasyRepository.save(rankingEasy);
                break;
            case MEDIUM:
                RankingMedium rankingMedium = modelMapper.map(record, RankingMedium.class);
                rankingMedium.setPlayer(player);
                ranking = rankingMediumRepository.save(rankingMedium);
                break;
            case HARD:
                RankingHard rankingHard = modelMapper.map(record, RankingHard.class);
                rankingHard.setPlayer(player);
                ranking = rankingHardRepository.save(rankingHard);
                break;
        }

        statisticsService.updateStatisticsWhenGameWon(level, player, record);
        return mapToRankingDto(ranking);
    }

    private RecordDto mapToRankingDto(Ranking ranking) {
        RecordDto recordDto = new ModelMapper().map(ranking, RecordDto.class);
        recordDto.setUsername(ranking.getPlayer().getUsername());
        return recordDto;
    }

    private Player getPlayer(String username) {
        return playerRepository.findByUsername(username).orElse(null);
    }

}
