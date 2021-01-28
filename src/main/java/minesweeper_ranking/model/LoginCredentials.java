package minesweeper_ranking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginCredentials {

    @Size(min = 4, max = 12, message = "Username must be between 4 and 12 characters.")
    @Pattern(regexp = "^[A-Z].*$", message = "Username must start with a capital character.")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Username can contain only alphabet characters.")
    private String username;

    @Size(min = 4, max = 30, message = "Password must be between 4 and 30 characters.")
    private String password;

}
