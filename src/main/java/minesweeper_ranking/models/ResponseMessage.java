package minesweeper_ranking.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage {

    private String message;

    @Override
    public String toString() {
        return "{\"message\": \"" + message + "\"}";
    }
}
