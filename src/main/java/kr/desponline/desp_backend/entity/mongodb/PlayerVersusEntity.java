package kr.desponline.desp_backend.entity.mongodb;

import kr.desponline.desp_backend.dto.PlayerVersusRecordDTO;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "PlayerVersus")
public class PlayerVersusEntity implements Comparable<PlayerVersusEntity> {

    private String nickname;
    private PlayerVersusRecordDTO record;

    public PlayerVersusEntity(String nickname, PlayerVersusRecordDTO record) {
        this.nickname = nickname;
        this.record = record;
    }

    @Override
    public int compareTo(PlayerVersusEntity o) {
        // 먼저 score로 비교
        int scoreCompare = Integer.compare(this.record.getScore(), o.record.getScore());
        if (scoreCompare != 0) {
            return -scoreCompare; // 내림차순으로 정렬하기 위해 부호를 반전
        }

        // score가 같을 경우 victoryRate를 계산하여 비교
        double thisVictoryRate = this.record.calculateVictoryRate();
        double otherVictoryRate = o.record.calculateVictoryRate();
        int victoryRateCompare = Double.compare(thisVictoryRate, otherVictoryRate);
        if (victoryRateCompare != 0) {
            return -victoryRateCompare; // 내림차순
        }

        // victoryRate도 같을 경우 전체 경기 수로 비교
        int thisTotal = this.record.calculateTotalMatches();
        int otherTotal = o.record.calculateTotalMatches();
        return -Integer.compare(thisTotal, otherTotal); // 내림차순
    }
}
