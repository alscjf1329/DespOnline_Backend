package kr.desponline.desp_backend.dto.web_event;

import java.time.LocalDateTime;
import java.util.Map;
import kr.desponline.desp_backend.entity.mongodb.web_event.WebEventType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WebEventDTO {

    private String id;
    private String title;
    private String description;
    private String bannerUri;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private WebEventType type;
    private Map<String, Object> details;
}
