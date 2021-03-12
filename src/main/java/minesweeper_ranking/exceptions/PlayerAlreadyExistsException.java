package minesweeper_ranking.exceptions;

public class PlayerAlreadyExistsException extends RuntimeException {

    public PlayerAlreadyExistsException(String username) {
        super("Player with username " + username + " already exists");
    }
}
