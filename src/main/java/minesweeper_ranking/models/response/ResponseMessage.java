package minesweeper_ranking.models.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseMessage {

    private String message;

    @Override
    public String toString() {
        return "{\"message\": \"" + message + "\"}";
    }
}
