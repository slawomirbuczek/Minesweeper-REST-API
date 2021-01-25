package minesweeper_ranking.exceptions;

import minesweeper_ranking.model.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ResponseMessage handleValidationExceptions(BindException ex) {

        List<String> errorMessages = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> errorMessages.add(error.getDefaultMessage()));

        Optional<String> message =  errorMessages.stream().reduce((s, s2) -> s + "\n" + s2);

        return new ResponseMessage(message.orElse("Validation Exception"));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseMessage handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        return new ResponseMessage(ex.getMessage());
    }
}
