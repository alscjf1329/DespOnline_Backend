package kr.desponline.desp_backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    NOT_FOUND(404, "COMMON-ERR-404", "PAGE NOT FOUND"),
    INTER_SERVER_ERROR(500, "COMMON-ERR-500", "INTER SERVER ERROR"),
    UNAUTHENTICATED_SERVER_USER(400, "MEMBER-ERR-400", "UNAUTHENTICATED SERVER USER"),
    ;

    private int status;
    private String errorCode;
    private String message;
}
