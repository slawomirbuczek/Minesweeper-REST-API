package minesweeper_ranking.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
public class Record {

    @NotNull
    private Level level;

    @NotNull
    private LocalDateTime date;

    @Min(0)
    private float time;

}
