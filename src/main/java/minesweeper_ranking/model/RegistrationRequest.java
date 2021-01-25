package minesweeper_ranking.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationRequest {

    @NotBlank(message = "Username is mandatory")
    @Size(min = 4, max = 15, message = "Username must be at least 4 characters long")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 4, max = 30, message = "Password must be at least 4 characters long")
    private String password;

}
