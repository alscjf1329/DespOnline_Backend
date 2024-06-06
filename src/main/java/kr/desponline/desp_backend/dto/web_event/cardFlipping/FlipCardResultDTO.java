package kr.desponline.desp_backend.dto.web_event.cardFlipping;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FlipCardResultDTO {

    private Boolean success;
    private List<Integer> flippedCardAnswer;
    private Integer remainingFlipCountInGame;
    private Integer remainingFlipOpportunity;
    private Integer rewardLevel;
    private List<Integer> cardStatus;
}
