package kr.desponline.desp_backend.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class MySQLTypeConvertServiceTest {

    private MySQLTypeConvertService service;

    @BeforeEach
    void setUp() {
        service = new MySQLTypeConvertService();
    }

    static List<Object[]> customDelimiterProvider() {
        return Arrays.asList(
            new Object[]{"1,2,3,4,5", ";", "1;2;3;4;5"},
            new Object[]{"apple,banana,orange,mango,grape", "|", "apple|banana|orange|mango|grape"}
        );
    }

    @ParameterizedTest
    @MethodSource("customDelimiterProvider")
    @DisplayName("Test listToString method with custom delimiter")
    void testListToStringWithCustomDelimiter(String input, String delimiter, String expected) {
        List<String> inputList = Arrays.asList(input.split(","));
        String result = service.listToString(inputList, delimiter);
        assertEquals(expected, result);
    }

    static List<Object[]> defaultDelimiterProvider() {
        return Arrays.asList(
            new Object[]{"1,2,3,4,5", "1,2,3,4,5"},
            new Object[]{"apple,banana,orange,mango,grape", "apple,banana,orange,mango,grape"}
        );
    }

    @ParameterizedTest
    @MethodSource("defaultDelimiterProvider")
    @DisplayName("Test listToString method with default delimiter")
    void testListToStringWithDefaultDelimiter(String input, String expected) {
        List<String> inputList = Arrays.asList(input.split(","));
        String result = service.listToString(inputList);
        assertEquals(expected, result);
    }

    static List<Object[]> customDelimiterStringToListProvider() {
        return Arrays.asList(
            new Object[]{"1;2;3;4;5", ";", new String[]{"1", "2", "3", "4", "5"}},
            new Object[]{"apple|banana|orange|mango|grape", "\\|", new String[]{"apple", "banana", "orange", "mango", "grape"}}
        );
    }

    @ParameterizedTest
    @MethodSource("customDelimiterStringToListProvider")
    @DisplayName("Test stringToList method with custom delimiter")
    void testStringToListWithCustomDelimiter(String input, String delimiter, String[] expected) {
        List<String> expectedResult = Arrays.asList(expected);
        List<String> result = service.stringToList(input, delimiter, Function.identity());
        assertIterableEquals(expectedResult, result);
    }

    static List<Object[]> defaultDelimiterStringToListProvider() {
        return Arrays.asList(
            new Object[]{"1,2,3,4,5", new String[]{"1", "2", "3", "4", "5"}},
            new Object[]{"apple,banana,orange,mango,grape", new String[]{"apple", "banana", "orange", "mango", "grape"}}
        );
    }

    @ParameterizedTest
    @MethodSource("defaultDelimiterStringToListProvider")
    @DisplayName("Test stringToList method with default delimiter")
    void testStringToListWithDefaultDelimiter(String input, String[] expected) {
        List<String> expectedResult = Arrays.asList(expected);
        List<String> result = service.stringToList(input, Function.identity());
        assertIterableEquals(expectedResult, result);
    }
}
