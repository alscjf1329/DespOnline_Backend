package kr.desponline.desp_backend.exception.customs;

import kr.desponline.desp_backend.exception.ErrorCode;
import lombok.Getter;

@Getter
public class SignupException extends RuntimeException {

    private ErrorCode errorCode;

    public SignupException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
