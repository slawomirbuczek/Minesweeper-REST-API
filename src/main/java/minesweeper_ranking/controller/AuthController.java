package minesweeper_ranking.controller;


import minesweeper_ranking.model.LoginRequest;
import minesweeper_ranking.model.RegistrationRequest;
import minesweeper_ranking.model.ResponseMessage;
import minesweeper_ranking.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static minesweeper_ranking.authentication.SecurityConstants.LOGIN_ENDPOINT;
import static minesweeper_ranking.authentication.SecurityConstants.REGISTRATION_ENDPOINT;

@RestController
public class AuthController {

    private final RegistrationService registrationService;

    public AuthController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping(LOGIN_ENDPOINT)
    public void login(@RequestBody LoginRequest loginRequest) {

    }

    @PostMapping(REGISTRATION_ENDPOINT)
    public ResponseEntity<ResponseMessage> register(@Valid @RequestBody RegistrationRequest registrationRequest) {
        return ResponseEntity.ok(registrationService.addPlayer(registrationRequest));
    }
}
