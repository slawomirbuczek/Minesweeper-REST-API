package minesweeper_ranking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginCredentials {

    @Size(min = 4, max = 12, message = "Username must be at least 4 characters long")
    private String username;

    @Size(min = 4, max = 30, message = "Password must be at least 4 characters long")
    private String password;

}
