package minesweeper_ranking.dto.player;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PlayerDto {

    private UUID id;

    private String username;

}
