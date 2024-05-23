package kr.desponline.desp_backend.controller.web_event;

import java.util.List;
import kr.desponline.desp_backend.dto.EventUserInfoResponseDTO;
import kr.desponline.desp_backend.dto.GameRewardMailRequestDTO;
import kr.desponline.desp_backend.dto.web_event.cardFlipping.CardFlippingUserDTO;
import kr.desponline.desp_backend.dto.web_event.cardFlipping.FlipCardResultDTO;
import kr.desponline.desp_backend.dto.web_event.cardFlipping.RequestFlipCardDTO;
import kr.desponline.desp_backend.dto.web_event.cardFlipping.ResetCardStatusResultDTO;
import kr.desponline.desp_backend.entity.mongodb.web_event.WebEventEntity;
import kr.desponline.desp_backend.entity.mysql.webgamedb.GameUserEntity;
import kr.desponline.desp_backend.service.RewardService;
import kr.desponline.desp_backend.service.SigninSessionService;
import kr.desponline.desp_backend.service.WebEventService;
import kr.desponline.desp_backend.service.web_event.CardFlippingService;
import kr.desponline.desp_backend.strategy.RandomIntegerListStrategy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/event/cardFlipping")
public class CardFlippingController {

    private final WebEventService webEventService;
    private final SigninSessionService signinSessionService;
    private final CardFlippingService cardFlippingService;
    private final RandomIntegerListStrategy randomIntegerListStrategy;
    private final RewardService gameMailService;

    public CardFlippingController(
        WebEventService webEventService,
        SigninSessionService signinSessionService,
        CardFlippingService cardFlippingService,
        RandomIntegerListStrategy randomIntegerListStrategy,
        RewardService gameMailService) {
        this.webEventService = webEventService;
        this.signinSessionService = signinSessionService;
        this.cardFlippingService = cardFlippingService;
        this.randomIntegerListStrategy = randomIntegerListStrategy;
        this.gameMailService = gameMailService;
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
        CardFlippingUserDTO cardFlippingDTO = cardFlippingService.findByUserUuidAndEventId(
            gameUser.getUuid(), eventId);

        if (cardFlippingDTO == null) {
            WebEventEntity event = webEventService.findById(eventId);
            CardFlippingUserDTO defaultCardFlippingDTO = CardFlippingUserDTO.createDefault(
                gameUser, event, randomIntegerListStrategy);
            cardFlippingService.save(defaultCardFlippingDTO);
        }

        return ResponseEntity.ok()
            .body(new EventUserInfoResponseDTO(webEventService.findById(eventId).toWebEventDTO(),
                cardFlippingDTO));
    }

    @PostMapping("/{eventId}/flip")
    public ResponseEntity<FlipCardResultDTO> flipCard(
        @RequestBody RequestFlipCardDTO requestFlipCardDTO,
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
        CardFlippingUserDTO cardFlippingDTO = cardFlippingService.findByUserUuidAndEventId(
            gameUser.getUuid(), eventId);

        if (cardFlippingDTO == null) {
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).build();
        }

        if (cardFlippingDTO.getFlipOpportunity() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        if (cardFlippingDTO.getRemainingFlipCount() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        WebEventEntity event = webEventService.findById(eventId);
        if (!cardFlippingService.validateRequestFlipCardDTO(event, cardFlippingDTO,
            requestFlipCardDTO)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        FlipCardResultDTO flipResult = cardFlippingDTO.flip(requestFlipCardDTO.getFlipIndexes());
        String randomRewardName = event.getRandomReward(flipResult.getRewardLevel() - 1);

        if (flipResult.getSuccess()) {
            GameRewardMailRequestDTO gameRewardMailRequestDTO = new GameRewardMailRequestDTO(
                List.of(cardFlippingDTO.getUser().getUuid()),
                event.getTitle(),
                (flipResult.getRewardLevel() - 1) + "단계 보상",
                randomRewardName,
                0.0
            );
            gameMailService.sendReward(gameRewardMailRequestDTO);
        }
        cardFlippingService.save(cardFlippingDTO);

        return ResponseEntity.ok().body(flipResult);
    }

    @PostMapping("/{eventId}/reset")
    public ResponseEntity<?> resetCards(
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
        CardFlippingUserDTO cardFlippingDTO = cardFlippingService.findByUserUuidAndEventId(
            gameUser.getUuid(), eventId);

        if (cardFlippingDTO == null) {
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).build();
        }

        if (cardFlippingDTO.getResetOpportunity() <= 0) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        WebEventEntity event = webEventService.findById(eventId);
        ResetCardStatusResultDTO resetCardStatusResultDTO = cardFlippingDTO.reset(event,
            randomIntegerListStrategy);
        cardFlippingService.save(cardFlippingDTO);

        return ResponseEntity.ok().body(resetCardStatusResultDTO);
    }
}
