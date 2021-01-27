package minesweeper_ranking.service;

import lombok.RequiredArgsConstructor;
import minesweeper_ranking.model.Level;
import minesweeper_ranking.model.Record;
import minesweeper_ranking.model.ResponseMessage;
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

@RequiredArgsConstructor
@Service
public class RankingService {

    private final LevelEasyRepository levelEasyRepository;
    private final LevelMediumRepository levelMediumRepository;
    private final LevelHardRepository levelHardRepository;

    public List<Record> getRanking(Level level) {
        Sort sort = Sort.by(Sort.Direction.ASC, "time");
        Pageable pageable = PageRequest.of(0, 50, sort);

        List<Record> ranking = new ArrayList<>();

        switch (level) {
            case EASY:
                ranking = mapToRecord(levelEasyRepository.findAll(pageable).getContent());
                break;
            case MEDIUM:
                ranking = mapToRecord(levelMediumRepository.findAll(pageable).getContent());
                break;
            case HARD:
                ranking = mapToRecord(levelHardRepository.findAll(pageable).getContent());
                break;
        }

        return ranking;
    }

    private List<Record> mapToRecord(List<? extends RankingLevel> ranking) {
        List<Record> recordList = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        ranking.forEach(rec -> recordList.add(modelMapper.map(rec, Record.class)));
        return recordList;
    }

    public ResponseMessage addRecord(Record record, Level level) {

        RankingLevel rankingLevel = new RankingLevel();
        rankingLevel.setDate(record.getDate());
        rankingLevel.setTime(record.getTime());
        rankingLevel.setUsername(getCurrentPlayer().getUsername());

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

}
