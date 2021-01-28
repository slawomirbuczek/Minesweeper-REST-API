package minesweeper_ranking.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import minesweeper_ranking.model.Level;
import minesweeper_ranking.model.Record;
import minesweeper_ranking.model.ResponseMessage;
import minesweeper_ranking.service.RankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/ranking/")
public class RankingController {

    private final RankingService rankingService;

    @GetMapping("/{level}")
    public List<Record> getRecords(@PathVariable(name = "level") Level level) {
        return rankingService.getRanking(level);
    }

    @PostMapping("/{level}")
    public ResponseEntity<ResponseMessage> addRecord(
            @Valid @RequestBody Record record,
            @PathVariable(name = "level") Level level) {
        return ResponseEntity.ok(rankingService.addRecord(record, level));
    }
}
