package minesweeper_ranking.controllers;

import lombok.AllArgsConstructor;
import minesweeper_ranking.dto.ranking.RecordDto;
import minesweeper_ranking.enums.Level;
import minesweeper_ranking.models.request.RequestRecord;
import minesweeper_ranking.services.RankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/ranking/")
public class RankingController {

    private final RankingService rankingService;

    @GetMapping("/{level}")
    public List<RecordDto> getRecords(@PathVariable(name = "level") Level level) {
        return rankingService.getRanking(level);
    }

    @PostMapping("/{level}")
    public ResponseEntity<RecordDto> addRecord(
            @Valid @RequestBody RequestRecord record,
            @PathVariable(name = "level") Level level,
            Principal principal) {
        return ResponseEntity.ok(rankingService.addRecord(record, level, principal.getName()));
    }
}
