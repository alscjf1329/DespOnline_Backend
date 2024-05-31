package kr.desponline.desp_backend.service;

import java.time.LocalDateTime;
import java.util.List;
import kr.desponline.desp_backend.entity.mongodb.web_event.WebEventEntity;
import kr.desponline.desp_backend.repository.mongodb.WebEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WebEventService {

    private final WebEventRepository webEventRepository;

    @Autowired
    public WebEventService(WebEventRepository webEventRepository) {
        this.webEventRepository = webEventRepository;
    }

    public List<WebEventEntity> findAllInPeriod() {
        return webEventRepository.findAllInPeriod(LocalDateTime.now()).collectList().block();
    }

    public WebEventEntity findById(String id) {
        return webEventRepository.findById(id).block();
    }
}
