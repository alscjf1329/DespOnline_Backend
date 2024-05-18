package kr.desponline.desp_backend.controller;

import java.util.List;
import kr.desponline.desp_backend.dto.web_event.WebEventDTO;
import kr.desponline.desp_backend.entity.mongodb.web_event.WebEventEntity;
import kr.desponline.desp_backend.service.WebEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event")
public class WebEventController {

    private final WebEventService webEventService;


    @Autowired
    public WebEventController(WebEventService webEventService) {
        this.webEventService = webEventService;
    }

    @GetMapping("")
    public ResponseEntity<List<WebEventDTO>> findAllEventInPeriod() {
        List<WebEventDTO> events = webEventService.findAllInPeriod().stream()
            .map(WebEventEntity::toWebEventDTO).toList();
        return ResponseEntity.ok().body(events);
    }
}
