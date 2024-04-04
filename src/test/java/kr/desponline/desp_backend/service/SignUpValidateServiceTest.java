package kr.desponline.desp_backend.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class SignUpValidateServiceTest {

    @ParameterizedTest
    @MethodSource("validIdAndPasswordProvider")
    public void testValidate_ValidIdAndPassword(String id, String password) {
        SignUpValidateService signUpValidateService = new SignUpValidateService();
        assertThat(signUpValidateService.validate(id, password)).isTrue();
    }

    private static Stream<Arguments> validIdAndPasswordProvider() {
        return Stream.of(
            Arguments.of("username123", "Password123!"),
            Arguments.of("anotheruser", "AnotherPassword123!"),
            Arguments.of("testuser", "TestPassword123!")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidIdAndPasswordProvider")
    public void testValidate_InvalidIdAndPassword(String id, String password) {
        SignUpValidateService signUpValidateService = new SignUpValidateService();
        assertThat(signUpValidateService.validate(id, password)).isFalse();
    }

    private static Stream<Arguments> invalidIdAndPasswordProvider() {
        return Stream.of(
            Arguments.of("abc", "Password123!"), // Short ID
            Arguments.of("verylongusername1234567890", "Password123!"), // Long ID
            Arguments.of("username123", "Password!"), // Missing digit
            Arguments.of("username123", "Password123"), // Missing special character
            Arguments.of("username123", "Password") // No special characters or digits
        );
    }
}
