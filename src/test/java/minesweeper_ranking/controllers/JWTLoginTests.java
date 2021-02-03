package minesweeper_ranking.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import minesweeper_ranking.dto.PlayerDto;
import minesweeper_ranking.entities.Player;
import minesweeper_ranking.models.ResponseMessage;
import minesweeper_ranking.repositories.PlayerRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class JWTLoginTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlayerRepository playerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static PlayerDto playerDto;

    @BeforeAll
    static void setUp() {
        playerDto = new PlayerDto("Anon", "password");
    }

    @Test
    void shouldReturnStatusUnauthorizedWhenCredentialsAreIncorrect() throws Exception {
        MvcResult result = mvc.perform(
                post("/api/login")
                        .content("application/json")
                        .content(objectMapper.writeValueAsString(playerDto)))
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(result.getResponse().getHeader(HttpHeaders.AUTHORIZATION)).isNull();
    }

    @Test
    void shouldReturnJWTWhenCredentialsAreCorrect() throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Player anon = modelMapper.map(playerDto, Player.class);
        anon.setPassword(passwordEncoder.encode(anon.getPassword()));
        given(playerRepository.findByUsername("Anon"))
                .willReturn(Optional.of(anon));

        MvcResult result = mvc.perform(
                post("/api/login")
                        .content("application/json")
                        .content(objectMapper.writeValueAsString(playerDto)))
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(new ResponseMessage("Logged in successfully").toString());

        assertThat(result.getResponse().getHeader(HttpHeaders.AUTHORIZATION)).contains("Bearer ");
    }

}
