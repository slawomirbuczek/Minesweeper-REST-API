package minesweeper_ranking.service;

import minesweeper_ranking.model.*;
import minesweeper_ranking.model.entity.*;
import minesweeper_ranking.repository.LevelEasyRepository;
import minesweeper_ranking.repository.LevelHardRepository;
import minesweeper_ranking.repository.LevelMediumRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RankingService {

    private final LevelEasyRepository levelEasyRepository;
    private final LevelMediumRepository levelMediumRepository;
    private final LevelHardRepository levelHardRepository;

    public RankingService(LevelEasyRepository levelEasyRepository, LevelMediumRepository levelMediumRepository, LevelHardRepository levelHardRepository) {
        this.levelEasyRepository = levelEasyRepository;
        this.levelMediumRepository = levelMediumRepository;
        this.levelHardRepository = levelHardRepository;
    }

    public List<RankingDTO> getRanking(Level level) {
        Sort sort = Sort.by(Sort.Direction.ASC, "time");
        Pageable pageable = PageRequest.of(0, 50, sort);

        List<RankingDTO> ranking = new ArrayList<>();

        switch (level) {
            case EASY:
                ranking = mapToRankingDTO(levelEasyRepository.findAll(pageable).getContent());
                break;
            case MEDIUM:
                ranking = mapToRankingDTO(levelMediumRepository.findAll(pageable).getContent());
                break;
            case HARD:
                ranking = mapToRankingDTO(levelHardRepository.findAll(pageable).getContent());
                break;
        }

        return ranking;
    }



    public ResponseMessage addRecord(Record record, Level level) {

        RankingLevel rankingLevel = new RankingLevel();
        rankingLevel.setDate(record.getDate());
        rankingLevel.setTime(record.getTime());
        rankingLevel.setPlayer(getCurrentPlayer());

        ModelMapper modelMapper = new ModelMapper();

        switch (level) {
            case EASY:
                levelEasyRepository.save(modelMapper.map(rankingLevel, LevelEasy.class));
                break;
            case MEDIUM:
                levelMediumRepository.save(modelMapper.map(rankingLevel, LevelMedium.class));
                break;
            case HARD:
                levelHardRepository.save(modelMapper.map(rankingLevel, LevelHard.class));
                break;
        }

        return new ResponseMessage("Record added!");
    }

    private Player getCurrentPlayer() {
        return (Player) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private List<RankingDTO> mapToRankingDTO(List<? extends RankingLevel> ranking) {
        List<RankingDTO> rankingDTO = new ArrayList<>();
        ranking.forEach(
                rankingLevel -> {
                    RankingDTO rank = new RankingDTO();
                    rank.setDate(rankingLevel.getDate());
                    rank.setTime(rankingLevel.getTime());
                    rank.setUsername(rankingLevel.getPlayer().getUsername());
                    rankingDTO.add(rank);
                }
        );
        return rankingDTO;
    }

}
