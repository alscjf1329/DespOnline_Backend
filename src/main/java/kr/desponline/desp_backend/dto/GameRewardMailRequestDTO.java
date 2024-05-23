package kr.desponline.desp_backend.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GameRewardMailRequestDTO {

    private List<String> uuids;
    private String senderName;
    private String letter;
    private String rewardName;
    private Double money;
}
