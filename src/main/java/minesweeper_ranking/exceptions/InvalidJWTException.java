package minesweeper_ranking.exceptions;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidJWTException extends JWTVerificationException {

    public InvalidJWTException() {
        super("Invalid JWT");
    }

}
