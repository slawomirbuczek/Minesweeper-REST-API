package minesweeper_ranking.service;

import lombok.AllArgsConstructor;
import minesweeper_ranking.dto.RankingDto;
import minesweeper_ranking.enums.Level;
import minesweeper_ranking.model.*;
import minesweeper_ranking.repository.RankingEasyRepository;
import minesweeper_ranking.repository.RankingHardRepository;
import minesweeper_ranking.repository.RankingMediumRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class RankingService {

    private final RankingEasyRepository rankingEasyRepository;
    private final RankingMediumRepository rankingMediumRepository;
    private final RankingHardRepository rankingHardRepository;

    public List<RankingDto> getRanking(Level level) {
        Sort sort = Sort.by(Sort.Direction.ASC, "time");
        Pageable pageable = PageRequest.of(0, 50, sort);

        List<RankingDto> ranking = new ArrayList<>();

        switch (level) {
            case EASY:
                ranking = mapToRecord(rankingEasyRepository.findAll(pageable).getContent());
                break;
            case MEDIUM:
                ranking = mapToRecord(rankingMediumRepository.findAll(pageable).getContent());
                break;
            case HARD:
                ranking = mapToRecord(rankingHardRepository.findAll(pageable).getContent());
                break;
        }

        return ranking;
    }

    private List<RankingDto> mapToRecord(List<? extends Ranking> ranking) {
        List<RankingDto> rankingDtoList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        ranking.forEach(rec -> rankingDtoList.add(modelMapper.map(rec, RankingDto.class)));
        return rankingDtoList;
    }

    public ResponseMessage addRecord(RankingDto rankingDto, Level level, Principal principal) {

        ModelMapper modelMapper = new ModelMapper();

        Ranking record = modelMapper.map(rankingDto, Ranking.class);

        record.setUsername(principal.getName());

        switch (level) {
            case EASY:
                rankingEasyRepository.save(modelMapper.map(record, RankingEasy.class));
                break;
            case MEDIUM:
                rankingMediumRepository.save(modelMapper.map(record, RankingMedium.class));
                break;
            case HARD:
                rankingHardRepository.save(modelMapper.map(record, RankingHard.class));
                break;
        }

        return new ResponseMessage("Record added!");
    }

}
