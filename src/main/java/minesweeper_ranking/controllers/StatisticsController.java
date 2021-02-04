package minesweeper_ranking.controllers;

import lombok.AllArgsConstructor;
import minesweeper_ranking.enums.Level;
import minesweeper_ranking.models.request.RequestRecord;
import minesweeper_ranking.models.response.ResponseStatistics;
import minesweeper_ranking.services.StatisticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/statistics")
@AllArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping
    public ResponseEntity<ResponseStatistics> getResponseStatistics(Principal principal) {
        return ResponseEntity.ok(statisticsService.getResponseStatistics(principal.getName()));
    }

    @PostMapping("/{level}")
    @ResponseStatus(HttpStatus.OK)
    public void updateStatisticsWhenGameLost(
            @Valid @RequestBody RequestRecord record,
            @PathVariable(name = "level") Level level,
            Principal principal) {
        statisticsService.updateStatisticsWhenGameLost(level, principal.getName(), record);
    }

}
