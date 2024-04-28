package kr.desponline.desp_backend.dto;

import kr.desponline.desp_backend.entity.mongodb.PlayerEntity;
import lombok.Getter;

@Getter
public class PlayerRecordDTO {

    private final PlayerEntity levelRank;
    private final PlayerVersusRecordDTO playerVersusRecord;

    public PlayerRecordDTO(PlayerEntity levelRank, PlayerVersusRecordDTO playerVersusRecord) {
        this.levelRank = levelRank;
        this.playerVersusRecord = playerVersusRecord;
    }
}
