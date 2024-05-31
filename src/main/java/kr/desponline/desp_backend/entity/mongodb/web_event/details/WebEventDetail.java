package kr.desponline.desp_backend.entity.mongodb.web_event.details;

import java.util.Map;

public class WebEventDetail {

    protected final Map<String, Object> details;

    public WebEventDetail(Map<String, Object> details) {
        this.details = details;
    }
}
