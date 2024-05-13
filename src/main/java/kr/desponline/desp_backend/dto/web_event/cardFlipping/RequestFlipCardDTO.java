package kr.desponline.desp_backend.dto.web_event.cardFlipping;

import java.util.List;
import lombok.Getter;

@Getter
public class RequestFlipCardDTO {

    private List<Integer> flipIndexes;
}
