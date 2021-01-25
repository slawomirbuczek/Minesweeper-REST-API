package minesweeper_ranking.controller;

import minesweeper_ranking.model.Level;
import minesweeper_ranking.model.RankingDTO;
import minesweeper_ranking.model.Record;
import minesweeper_ranking.model.ResponseMessage;
import minesweeper_ranking.service.RankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/ranking/")
public class RankingController {

    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping("/{level}")
    public List<RankingDTO> getRecords(@PathVariable(name = "level") Level level) {
        return rankingService.getRanking(level);
    }

    @PostMapping("/{level}")
    public ResponseEntity<ResponseMessage> addRecord(
            @Valid @RequestBody Record record,
            @PathVariable(name = "level") Level level) {
        return ResponseEntity.ok(rankingService.addRecord(record, level));
    }
}
