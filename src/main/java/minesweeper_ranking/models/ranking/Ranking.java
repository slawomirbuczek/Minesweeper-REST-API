package minesweeper_ranking.models.ranking;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import minesweeper_ranking.enums.Level;
import minesweeper_ranking.models.player.Player;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Ranking")
public class Ranking {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate date;

    private Float time;

    private Level level;

    @ManyToOne
    private Player player;

}
