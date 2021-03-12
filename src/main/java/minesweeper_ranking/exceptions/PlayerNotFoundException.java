package minesweeper_ranking.exceptions;

public class PlayerNotFoundException extends RuntimeException{
    public PlayerNotFoundException(String username) {
        super("Player with username " + username + " not found");
    }
}
