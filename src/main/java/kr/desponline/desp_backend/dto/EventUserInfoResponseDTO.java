package kr.desponline.desp_backend.dto;

import kr.desponline.desp_backend.entity.mongodb.web_event.WebEventEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventUserInfoResponseDTO {

    private WebEventEntity eventInfo;
    private Object eventUserInfo;
}
