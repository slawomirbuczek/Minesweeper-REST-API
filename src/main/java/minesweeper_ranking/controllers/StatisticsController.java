package minesweeper_ranking.controllers;

import lombok.AllArgsConstructor;
import minesweeper_ranking.models.Statistics;
import minesweeper_ranking.services.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("api/statistics")
@AllArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @GetMapping
    public ResponseEntity<Statistics> getStatistics(Principal principal) {
        return ResponseEntity.ok(statisticsService.getStatistics(principal.getName()));
    }

}
