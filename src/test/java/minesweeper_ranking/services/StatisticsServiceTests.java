package minesweeper_ranking.services;

import minesweeper_ranking.repositories.statistics.StatisticsRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
public class StatisticsServiceTests {

    @Mock
    private PlayerService playerService;

    @Mock
    private StatisticsRepository statisticsRepository;

    @Spy
    private ModelMapper mapper;

    @InjectMocks
    private StatisticsService statisticsService;



}
