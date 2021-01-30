package minesweeper_ranking.controller;

import lombok.AllArgsConstructor;
import minesweeper_ranking.enums.Level;
import minesweeper_ranking.dto.RankingDto;
import minesweeper_ranking.model.ResponseMessage;
import minesweeper_ranking.service.RankingService;
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
    public List<RankingDto> getRecords(@PathVariable(name = "level") Level level) {
        return rankingService.getRanking(level);
    }

    @PostMapping("/{level}")
    public ResponseEntity<ResponseMessage> addRecord(
            @Valid @RequestBody RankingDto rankingDto,
            @PathVariable(name = "level") Level level,
            Principal principal) {
        return ResponseEntity.ok(rankingService.addRecord(rankingDto, level, principal));
    }
}
