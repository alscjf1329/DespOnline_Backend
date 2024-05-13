package kr.desponline.desp_backend.service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MySQLTypeConvertService {

    public static String DEFAULT_DELIMITER = ",";

    public String listToString(List<?> arg, String delimiter) {
        List<String> stringList = arg.stream().map(Object::toString).toList();
        return String.join(delimiter, stringList);
    }

    public String listToString(List<?> arg) {
        List<String> stringList = arg.stream().map(e -> {
            if (e == null) {
                return "null";
            }
            return e.toString();
        }).toList();
        return String.join(DEFAULT_DELIMITER, stringList);
    }

    public <T> List<T> stringToList(String arg, String delimiter, Function<String, T> converter) {
        return Arrays.stream(arg.split(delimiter))
            .map(converter)
            .collect(Collectors.toList());
    }

    public <T> List<T> stringToList(String arg, Function<String, T> converter) {
        return Arrays.stream(arg.split(DEFAULT_DELIMITER))
            .map(e -> {
                if (e.equals("null")) {
                    return null;
                }
                return converter.apply(e);
            })
            .collect(Collectors.toList());
    }
}
