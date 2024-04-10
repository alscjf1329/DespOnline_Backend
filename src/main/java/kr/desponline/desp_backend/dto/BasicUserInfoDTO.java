package kr.desponline.desp_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class BasicUserInfoDTO {

    @JsonProperty("name")
    private String nickname;
    @JsonProperty("id")
    private String uuid;
}
