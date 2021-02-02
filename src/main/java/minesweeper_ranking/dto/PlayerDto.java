package minesweeper_ranking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto {

    @Size(min = 4, message = "Username is too short")
    @Size(max = 15, message = "Username is too long")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Username can contain only alphabet characters")
    private String username;

    @Size(min = 4, message = "Password is too short")
    @Size(max = 30, message = "Password is too long")
    private String password;

}
