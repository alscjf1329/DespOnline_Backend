package kr.desponline.desp_backend.service.web_event;

import kr.desponline.desp_backend.dto.web_event.cardFlipping.CardFlippingUserDTO;
import kr.desponline.desp_backend.dto.web_event.cardFlipping.RequestFlipCardDTO;
import kr.desponline.desp_backend.entity.mongodb.web_event.WebEventEntity;
import kr.desponline.desp_backend.entity.mongodb.web_event.details.CardFlippingDetail;
import kr.desponline.desp_backend.entity.mysql.webgamedb.CardFlippingUserEntity;
import kr.desponline.desp_backend.repository.mysql.webgamedb.CardFlippingRepository;
import org.springframework.stereotype.Service;

@Service
public class CardFlippingService {

    private final CardFlippingRepository cardFlippingRepository;

    public CardFlippingService(CardFlippingRepository cardFlippingRepository) {
        this.cardFlippingRepository = cardFlippingRepository;
    }

    public CardFlippingUserDTO findByUserUuidAndEventId(String uuid, String eventId) {
        CardFlippingUserEntity cardFlipping = cardFlippingRepository.findByUserUuidAndEventId(
            uuid, eventId);
        if (cardFlipping == null) {
            return null;
        }
        return cardFlipping.toCardFlippingDTO();
    }

    public void save(CardFlippingUserDTO processedCardFlippingEntity) {
        cardFlippingRepository.save(processedCardFlippingEntity.toCardFlippingEntity());
    }

    public boolean validateRequestFlipCardDTO(
        WebEventEntity event,
        CardFlippingUserDTO cardFlippingUserDTO,
        RequestFlipCardDTO requestFlipCardDTO) {
        CardFlippingDetail details = (CardFlippingDetail) event.getDetails();
        if (requestFlipCardDTO.getFlipIndexes().size() != details.getFlipCount()) {
            return false;
        }

        if (cardFlippingUserDTO.checkFlipped(requestFlipCardDTO.getFlipIndexes())) {
            return false;
        }

        for (Integer index : requestFlipCardDTO.getFlipIndexes()) {
            if (index == null || index < 0 || index > details.getSize() - 1) {
                return false;
            }
        }
        return true;
    }
}
