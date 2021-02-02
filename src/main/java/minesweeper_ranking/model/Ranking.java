package minesweeper_ranking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ranking {

    protected UUID id;

    protected Date date;

    protected float time;

    protected String username;

}
