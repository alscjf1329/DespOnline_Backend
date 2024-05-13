package kr.desponline.desp_backend.controller;

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
    public WebEventController(
        WebEventService webEventService) {
        this.webEventService = webEventService;
    }

    @GetMapping("")
    public ResponseEntity<?> findAllEventInPeriod() {
        return ResponseEntity.ok().body(webEventService.findAllInPeriod());
    }
}
