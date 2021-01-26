package minesweeper_ranking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Date;


@Data
@NoArgsConstructor
public class Record {

    @NotNull
    private Date date;

    @Min(0)
    private float time;

    @Null
    private String username;

}
