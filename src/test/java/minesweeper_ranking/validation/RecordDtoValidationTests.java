package minesweeper_ranking.validation;

import minesweeper_ranking.models.ranking.RequestRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class RecordDtoValidationTests {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassWhenDataIsCorrect() {
        RequestRecord record = new RequestRecord(12, LocalDate.now());

        assertThat(validator.validate(record)).isEmpty();
    }

    @Test
    void shouldNotPassWhenDateIsNull() {
        RequestRecord record = new RequestRecord(12, null);

        assertThat(validator.validate(record)).hasSize(1);
    }

    @Test
    void shouldNotPassWhenTimeIsLessThanZero() {
        RequestRecord record = new RequestRecord(-1, LocalDate.now());

        assertThat(validator.validate(record)).hasSize(1);
    }

}
