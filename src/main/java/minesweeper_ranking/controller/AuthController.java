package minesweeper_ranking.controller;


import minesweeper_ranking.model.LoginCredentials;
import minesweeper_ranking.model.ResponseMessage;
import minesweeper_ranking.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static minesweeper_ranking.authentication.ApiProperties.LOGIN_ENDPOINT;
import static minesweeper_ranking.authentication.ApiProperties.REGISTRATION_ENDPOINT;

@RestController
public class AuthController {

    private final RegistrationService registrationService;

    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(LOGIN_ENDPOINT)
    public void login(@RequestBody LoginCredentials loginCredentials) {

    }

    @PostMapping(REGISTRATION_ENDPOINT)
    public ResponseEntity<ResponseMessage> register(@Valid @RequestBody LoginCredentials loginCredentials) {
        return ResponseEntity.ok(registrationService.addPlayer(loginCredentials));
    }
}
