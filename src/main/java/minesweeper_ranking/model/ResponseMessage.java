package minesweeper_ranking.model;

import lombok.*;

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
