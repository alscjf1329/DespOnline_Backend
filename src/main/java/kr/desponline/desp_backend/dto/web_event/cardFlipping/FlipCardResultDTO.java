package kr.desponline.desp_backend.dto.web_event.cardFlipping;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FlipCardResultDTO {

    private boolean success;
    private List<Integer> flippedCardAnswer;
}
