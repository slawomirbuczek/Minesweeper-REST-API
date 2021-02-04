package minesweeper_ranking.dto.ranking;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class RecordDto {

    private String username;

    private Date date;

    private float time;

}
