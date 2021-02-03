package minesweeper_ranking.controllers;


import lombok.AllArgsConstructor;
import minesweeper_ranking.dto.PlayerDto;
import minesweeper_ranking.models.ResponseMessage;
import minesweeper_ranking.services.RegistrationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@AllArgsConstructor
public class AuthController {

    private final RegistrationService registrationService;

    @PostMapping("/api/login")
    public void login(@RequestBody PlayerDto loginCredentials) {

    }

    @PostMapping("/api/register")
    public ResponseEntity<ResponseMessage> register(@Valid @RequestBody PlayerDto player) {
        return ResponseEntity.ok(registrationService.addPlayer(player));
    }
}
