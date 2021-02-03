package minesweeper_ranking.validation;


import minesweeper_ranking.dto.PlayerDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerDtoValidationTests {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassWhenDataIsCorrect() {
        PlayerDto playerDto = new PlayerDto(generateStringWithLength(5), generateStringWithLength(5));

        assertThat(validator.validate(playerDto)).isEmpty();
    }

    @Test
    void shouldReturnViolationWhenUsernameIsTooShort() {
        PlayerDto playerDto = new PlayerDto(generateStringWithLength(2), generateStringWithLength(5));

        Set<ConstraintViolation<PlayerDto>> violations = validator.validate(playerDto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Username is too short");
    }

    @Test
    void shouldReturnViolationWhenUsernameIsTooLong() {
        PlayerDto playerDto = new PlayerDto(generateStringWithLength(16), generateStringWithLength(5));

        Set<ConstraintViolation<PlayerDto>> violations = validator.validate(playerDto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Username is too long");
    }

    @Test
    void shouldReturnViolationWhenUsernameContainsNumbers() {
        PlayerDto playerDto = new PlayerDto(generateStringWithLength(5) + 7, generateStringWithLength(5));

        Set<ConstraintViolation<PlayerDto>> violations = validator.validate(playerDto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Username can contain only alphabet characters");
    }

    @Test
    void shouldReturnViolationWhenPasswordIsTooShort() {
        PlayerDto playerDto = new PlayerDto(generateStringWithLength(5), generateStringWithLength(2));

        Set<ConstraintViolation<PlayerDto>> violations = validator.validate(playerDto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Password is too short");
    }

    @Test
    void shouldReturnViolationWhenPasswordIsTooLong() {
        PlayerDto playerDto = new PlayerDto(generateStringWithLength(5), generateStringWithLength(31));

        Set<ConstraintViolation<PlayerDto>> violations = validator.validate(playerDto);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Password is too long");
    }

    private String generateStringWithLength(int length){
        char[] array = new char[length];
        Arrays.fill(array, 'a');
        return new String(array);
    }

}
