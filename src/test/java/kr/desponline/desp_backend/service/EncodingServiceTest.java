package kr.desponline.desp_backend.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

public class EncodingServiceTest {

    @InjectMocks
    private EncodingService encodingService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest
    @CsvSource({
        "password1",
        "password2",
        "password3",
    })
    public void testEncode(String rawPassword) {
        // Given
        String encodedPassword = encodingService.encode(rawPassword);
        // Then
        assertTrue(encodingService.matches(rawPassword, encodedPassword));
    }
}
