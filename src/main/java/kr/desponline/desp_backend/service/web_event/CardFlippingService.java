package kr.desponline.desp_backend.service.web_event;

import kr.desponline.desp_backend.dto.web_event.CardFlippingDTO;
import kr.desponline.desp_backend.mysql_repository.webgamedb.CardFlippingRepository;
import org.springframework.stereotype.Service;

@Service
public class CardFlippingService {

    private final CardFlippingRepository cardFlippingRepository;

    public CardFlippingService(CardFlippingRepository cardFlippingRepository) {
        this.cardFlippingRepository = cardFlippingRepository;
    }

    public CardFlippingDTO findByUserUuidAndEventId(String uuid, String eventId) {
        return cardFlippingRepository.findByUserUuidAndEventId(uuid, eventId)
            .toCardFlippingDTO();
    }

    public void save(CardFlippingDTO processedCardFlippingEntity) {
        cardFlippingRepository.save(processedCardFlippingEntity.toCardFlippingEntity());
    }
}
