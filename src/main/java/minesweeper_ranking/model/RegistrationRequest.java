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

    @NotBlank(message = "Name is mandatory")
    @Size(min = 4, max = 15, message = "min - 4, max - 15")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 4, max = 15, message = "min - 30, max - 40")
    private String password;

}
