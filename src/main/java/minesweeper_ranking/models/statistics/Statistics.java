package minesweeper_ranking.models.statistics;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import minesweeper_ranking.enums.Level;
import minesweeper_ranking.models.player.Player;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Statistics")
public class Statistics {

    @Id
    @GeneratedValue
    private Long id;

    private Integer totalGamesPlayed;

    private Integer gamesWon;

    private Float totalTime;

    private Float averageTime;

    private Float bestTime;

    private Level level;

    @OneToOne
    private Player player;

}
