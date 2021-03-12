package minesweeper_ranking.controllers;


import lombok.AllArgsConstructor;
import minesweeper_ranking.models.player.RequestCredentials;
import minesweeper_ranking.models.ResponseMessage;
import minesweeper_ranking.services.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@AllArgsConstructor
public class AuthController {

    private final PlayerService playerService;

    @PostMapping("/api/login")
    public void login(@RequestBody RequestCredentials loginRequestCredentials) {

    }

    @PostMapping("/api/register")
    public ResponseEntity<ResponseMessage> register(@Valid @RequestBody RequestCredentials player) {
        return ResponseEntity.ok(playerService.addPlayer(player));
    }
}
