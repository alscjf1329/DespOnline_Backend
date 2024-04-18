package kr.desponline.desp_backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BasicUserInfoDTO {

    @JsonProperty("name")
    private String nickname;
    @JsonProperty("id")
    private String uuid;
}
