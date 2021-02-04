package minesweeper_ranking.models.ranking;

import minesweeper_ranking.models.player.Player;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "ranking_medium", schema = "public")
public class RankingMedium extends Ranking {

    @Id
    @GeneratedValue
    @Override
    public UUID getId() {
        return super.getId();
    }

    @Override
    @ManyToOne
    public Player getPlayer() {
        return super.getPlayer();
    }

    @Override
    public Date getDate() {
        return super.getDate();
    }

    @Override
    public float getTime() {
        return super.getTime();
    }

}
