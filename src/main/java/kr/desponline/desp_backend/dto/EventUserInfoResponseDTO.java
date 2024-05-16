package kr.desponline.desp_backend.dto;

import kr.desponline.desp_backend.dto.web_event.WebEventDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventUserInfoResponseDTO {

    private WebEventDTO eventInfo;
    private Object eventUserInfo;
}
