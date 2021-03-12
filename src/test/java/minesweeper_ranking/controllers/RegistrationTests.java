package minesweeper_ranking.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import minesweeper_ranking.models.request.RequestCredentials;
import minesweeper_ranking.models.response.ResponseMessage;
import minesweeper_ranking.services.PlayerService;
import minesweeper_ranking.authentication.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
public class RegistrationTests {

    @MockBean
    private PlayerService playerService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    void shouldRegisterNewPlayerWhenCredentialsAreCorrect() throws Exception {
        ResponseMessage response = new ResponseMessage("Registered successfully");

        given(playerService.addPlayer(any(RequestCredentials.class))).willReturn(response);

        RequestCredentials requestCredentials = new RequestCredentials("Anon", "password");

        mvc.perform(
                post("/api/register")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(requestCredentials)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)));

    }

}
