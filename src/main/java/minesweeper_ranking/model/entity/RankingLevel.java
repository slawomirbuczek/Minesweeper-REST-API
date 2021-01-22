package minesweeper_ranking.model.entity;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RankingLevel {

    protected UUID id;

    protected LocalDateTime date;

    protected float time;

    protected Player player;

}
