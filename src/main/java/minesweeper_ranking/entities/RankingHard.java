package minesweeper_ranking.entities;

import minesweeper_ranking.models.Ranking;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "ranking_hard", schema = "public")
public class RankingHard extends Ranking {

    @Id
    @GeneratedValue(generator = "UUID")
    @Override
    public UUID getId() {
        return super.getId();
    }

    @Override
    public Date getDate() {
        return super.getDate();
    }

    @Override
    public float getTime() {
        return super.getTime();
    }

    @Override
    public String getUsername() {
        return super.getUsername();
    }
}
