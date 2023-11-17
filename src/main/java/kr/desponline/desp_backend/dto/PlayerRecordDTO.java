package kr.desponline.desp_backend.dto;

import lombok.Getter;

@Getter
public class PlayerRecordDTO {
    private final PlayerDTO levelRank;
    private final PlayerVersusRecordDTO playerVersusRecord;

    public PlayerRecordDTO(PlayerDTO levelRank, PlayerVersusRecordDTO playerVersusRecord) {
        this.levelRank = levelRank;
        this.playerVersusRecord = playerVersusRecord;
    }
}
