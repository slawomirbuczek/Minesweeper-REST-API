package minesweeper_ranking.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RankingDTO {

    private Date date;

    private float time;

    private String username;

}
