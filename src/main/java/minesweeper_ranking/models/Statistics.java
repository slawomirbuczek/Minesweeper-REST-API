package minesweeper_ranking.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {

    private int easyGamesWon;

    private float averageEasyGameTime;

    private int mediumGamesWon;

    private float averageMediumGameTime;

    private int hardGamesWon;

    private float averageHardGameTime;


}
