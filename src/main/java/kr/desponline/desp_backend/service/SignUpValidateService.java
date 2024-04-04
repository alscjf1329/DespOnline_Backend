package kr.desponline.desp_backend.service;

import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

@Service
public class SignUpValidateService {

    Pattern ID_PATTERN = Pattern.compile("^[a-z0-9]{8,20}$");
    Pattern PW_PATTERN = Pattern.compile(
        "^.*(?=^.{8,30}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$");

    public boolean validate(String id, String pw) {
        return validateId(id) && validatePw(pw);
    }

    public boolean validateId(String id) {
        return ID_PATTERN.matcher(id).matches();
    }

    public boolean validatePw(String pw) {
        return PW_PATTERN.matcher(pw).matches();
    }
}
