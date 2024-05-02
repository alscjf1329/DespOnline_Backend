package kr.desponline.desp_backend.dto;

import lombok.Getter;

@Getter
public class SignupRequestDTO {

    private String nickname;
    private String authenticationCode;
    private String id;
    private String password;
}
