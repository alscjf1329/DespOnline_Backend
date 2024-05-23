package kr.desponline.desp_backend.dto.web_event.cardFlipping;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResetCardStatusResultDTO {

    private Integer remainingResetOpportunity;
    private Integer rewardLevel;
    private List<Integer> cardStatus;
}
