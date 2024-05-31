package kr.desponline.desp_backend.entity.mongodb.web_event;

import kr.desponline.desp_backend.entity.mongodb.web_event.details.CardFlippingDetail;
import kr.desponline.desp_backend.entity.mongodb.web_event.details.WebEventDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum WebEventType {
    cardFlipping(CardFlippingDetail.class);
    private final Class<? extends WebEventDetail> detailClazz;
}
