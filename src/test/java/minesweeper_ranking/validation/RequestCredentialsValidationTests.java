package minesweeper_ranking.validation;


import minesweeper_ranking.models.request.RequestCredentials;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Arrays;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RequestCredentialsValidationTests {

    private static Validator validator;

    @BeforeAll
    public static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void shouldPassWhenDataIsCorrect() {
        RequestCredentials requestCredentials = new RequestCredentials(generateStringWithLength(5), generateStringWithLength(5));

        assertThat(validator.validate(requestCredentials)).isEmpty();
    }

    @Test
    void shouldReturnViolationWhenUsernameIsTooShort() {
        RequestCredentials requestCredentials = new RequestCredentials(generateStringWithLength(2), generateStringWithLength(5));

        Set<ConstraintViolation<RequestCredentials>> violations = validator.validate(requestCredentials);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Username is too short");
    }

    @Test
    void shouldReturnViolationWhenUsernameIsTooLong() {
        RequestCredentials requestCredentials = new RequestCredentials(generateStringWithLength(16), generateStringWithLength(5));

        Set<ConstraintViolation<RequestCredentials>> violations = validator.validate(requestCredentials);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Username is too long");
    }

    @Test
    void shouldReturnViolationWhenUsernameContainsNumbers() {
        RequestCredentials requestCredentials = new RequestCredentials(generateStringWithLength(5) + 7, generateStringWithLength(5));

        Set<ConstraintViolation<RequestCredentials>> violations = validator.validate(requestCredentials);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Username can contain only alphabet characters");
    }

    @Test
    void shouldReturnViolationWhenPasswordIsTooShort() {
        RequestCredentials requestCredentials = new RequestCredentials(generateStringWithLength(5), generateStringWithLength(2));

        Set<ConstraintViolation<RequestCredentials>> violations = validator.validate(requestCredentials);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Password is too short");
    }

    @Test
    void shouldReturnViolationWhenPasswordIsTooLong() {
        RequestCredentials requestCredentials = new RequestCredentials(generateStringWithLength(5), generateStringWithLength(31));

        Set<ConstraintViolation<RequestCredentials>> violations = validator.validate(requestCredentials);

        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("Password is too long");
    }

    private String generateStringWithLength(int length){
        char[] array = new char[length];
        Arrays.fill(array, 'a');
        return new String(array);
    }

}
