package kr.desponline.desp_backend.controller.web_event;

import kr.desponline.desp_backend.dto.EventUserInfoResponseDTO;
import kr.desponline.desp_backend.dto.web_event.CardFlippingDTO;
import kr.desponline.desp_backend.entity.mongodb.web_event.WebEventEntity;
import kr.desponline.desp_backend.entity.mysql.webgamedb.GameUserEntity;
import kr.desponline.desp_backend.service.SigninSessionService;
import kr.desponline.desp_backend.service.WebEventService;
import kr.desponline.desp_backend.service.web_event.CardFlippingService;
import kr.desponline.desp_backend.strategy.RandomIntegerListStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event/cardFlipping")
public class CardFlippingController {

    private final WebEventService webEventService;
    private final SigninSessionService signinSessionService;
    private final CardFlippingService cardFlippingService;
    private final RandomIntegerListStrategy randomIntegerListStrategy;

    public CardFlippingController(
        WebEventService webEventService,
        SigninSessionService signinSessionService,
        CardFlippingService cardFlippingService,
        RandomIntegerListStrategy randomIntegerListStrategy
    ) {
        this.webEventService = webEventService;
        this.signinSessionService = signinSessionService;
        this.cardFlippingService = cardFlippingService;
        this.randomIntegerListStrategy = randomIntegerListStrategy;
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<EventUserInfoResponseDTO> showCardFlippingEvent(
        @CookieValue(value = SigninSessionService.SESSION_KEY_COOKIE_NAME, required = false) String sessionKey,
        @PathVariable("eventId") String eventId
    ) {
        if (sessionKey == null) {
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).build();
        }

        GameUserEntity gameUser = signinSessionService.findSession(sessionKey);
        if (gameUser == null) {
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).build();
        }
        CardFlippingDTO cardFlippingDTO = cardFlippingService.findByUserUuidAndEventId(
            gameUser.getUuid(), eventId);

        if (cardFlippingDTO == null) {
            WebEventEntity event = webEventService.findById(eventId);
            CardFlippingDTO defaultCardFlippingDTO = CardFlippingDTO.createDefault(
                gameUser, event, randomIntegerListStrategy);
            cardFlippingService.save(defaultCardFlippingDTO);
        }

        return ResponseEntity.ok()
            .body(new EventUserInfoResponseDTO(webEventService.findById(eventId), cardFlippingDTO));
    }
}
