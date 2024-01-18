package kr.desponline.desp_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccessCredentialDTO {
    private String nickname;
    private String authenticationCode;
}
