package minesweeper_ranking.controller;


import minesweeper_ranking.model.LoginCredentials;
import minesweeper_ranking.model.ResponseMessage;
import minesweeper_ranking.service.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
public class AuthController {

    private final RegistrationService registrationService;


    public AuthController(
            RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/api/login")
    public void login(@RequestBody LoginCredentials loginCredentials) {

    }

    @PostMapping("/api/register")
    public ResponseEntity<ResponseMessage> register(@Valid @RequestBody LoginCredentials loginCredentials) {
        return ResponseEntity.ok(registrationService.addPlayer(loginCredentials));
    }
}
