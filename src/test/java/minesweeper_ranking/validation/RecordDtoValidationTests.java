package minesweeper_ranking.validation;

import minesweeper_ranking.models.request.RequestRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Date;

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
        RequestRecord record = new RequestRecord(12, new Date());

        assertThat(validator.validate(record)).isEmpty();
    }

    @Test
    void shouldNotPassWhenDateIsNull() {
        RequestRecord record = new RequestRecord(12, null);

        assertThat(validator.validate(record)).hasSize(1);
    }

    @Test
    void shouldNotPassWhenTimeIsLessThanZero() {
        RequestRecord record = new RequestRecord(-1, new Date());

        assertThat(validator.validate(record)).hasSize(1);
    }

}
