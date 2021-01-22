package minesweeper_ranking.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class RankingDTO {

    private LocalDateTime date;

    private float time;

    private String username;

}
