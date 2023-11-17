package kr.desponline.desp_backend.dto;

import lombok.Data;

@Data
public class PlayerVersusRecordDTO {
    private int score;
    private int victory;
    private int defeat;
    private int draw;

    public PlayerVersusRecordDTO(int score, int victory, int defeat, int draw) {
        this.score = score;
        this.victory = victory;
        this.defeat = defeat;
        this.draw = draw;
    }
}
