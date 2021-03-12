package minesweeper_ranking.exceptions;

import minesweeper_ranking.models.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GlobalExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseMessage handleValidationExceptions(BindException ex) {

        List<String> errorMessages = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));

        Optional<String> message =  errorMessages.stream().reduce((s, s2) -> s + "\n" + s2);

        return new ResponseMessage(message.orElse("Validation Exception"));
    }

    @ExceptionHandler(PlayerAlreadyExistsException.class)
    public ResponseMessage handleUserAlreadyExistsException(PlayerAlreadyExistsException ex) {
        return new ResponseMessage(ex.getMessage());
    }

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseMessage handlePlayerNotFoundException(PlayerNotFoundException ex) {
        return new ResponseMessage(ex.getMessage());
    }

}
