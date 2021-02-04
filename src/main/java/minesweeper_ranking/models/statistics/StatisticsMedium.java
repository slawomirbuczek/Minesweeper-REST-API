package minesweeper_ranking.models.statistics;

import minesweeper_ranking.models.player.Player;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "statistics_medium")
public class StatisticsMedium extends Statistics {

    @Id
    @GeneratedValue
    @Override
    public UUID getId() {
        return super.getId();
    }

    @Override
    @OneToOne
    public Player getPlayer() {
        return super.getPlayer();
    }

    @Override
    public int getTotalGamesPlayed() {
        return super.getTotalGamesPlayed();
    }

    @Override
    public int getGamesWon() {
        return super.getGamesWon();
    }

    @Override
    public float getTotalTime() {
        return super.getTotalTime();
    }

    @Override
    public float getAverageTime() {
        return super.getAverageTime();
    }

    @Override
    public float getBestTime() {
        return super.getBestTime();
    }

}
