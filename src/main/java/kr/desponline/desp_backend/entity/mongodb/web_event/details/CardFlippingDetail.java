package kr.desponline.desp_backend.entity.mongodb.web_event.details;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CardFlippingDetail {

    @JsonProperty("size")
    private Integer size;

    @JsonProperty("rewards")
    private List<List<String>> rewards;
}
