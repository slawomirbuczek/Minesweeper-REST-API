package minesweeper_ranking.model.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
public class LevelEasy extends RankingLevel{

    @Id
    @GeneratedValue(generator = "UUID")
    @Override
    public UUID getId() {
        return super.getId();
    }

    @Override
    public LocalDateTime getDate() {
        return super.getDate();
    }

    @Override
    public float getTime() {
        return super.getTime();
    }

    @ManyToOne
    @JoinColumn(name = "player_id")
    @Override
    public Player getPlayer() {
        return super.getPlayer();
    }

}
