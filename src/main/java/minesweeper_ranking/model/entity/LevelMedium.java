package minesweeper_ranking.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "LEVEL_MEDIUM", schema = "public")
public class LevelMedium extends RankingLevel {

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
