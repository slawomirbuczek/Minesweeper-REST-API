package minesweeper_ranking.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import minesweeper_ranking.models.Statistics;
import minesweeper_ranking.services.StatisticsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
public class StatisticsControllerTests {

    @MockBean
    private StatisticsService statisticsService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "Anon")
    void shouldReturnStatistics() throws Exception {
        given(statisticsService.getStatistics("Anon")).willReturn(getStatistics());

        MvcResult result = mvc.perform(
                get("/api/statistics"))
                .andReturn();

        assertThat(result.getResponse().getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(objectMapper.writeValueAsString(getStatistics()));
    }

    private Statistics getStatistics() {
        Statistics statistics = new Statistics();
        statistics.setEasyGamesWon(5);
        statistics.setMediumGamesWon(10);
        statistics.setHardGamesWon(15);
        statistics.setAverageEasyGameTime(11);
        statistics.setAverageMediumGameTime(12);
        statistics.setAverageHardGameTime(13);
        return statistics;
    }

}
