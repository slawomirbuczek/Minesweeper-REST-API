package minesweeper_ranking.models.ranking;

import lombok.*;
import minesweeper_ranking.models.player.Player;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public abstract class Ranking {

    private UUID id;

    private Player player;

    private Date date;

    private float time;


}
