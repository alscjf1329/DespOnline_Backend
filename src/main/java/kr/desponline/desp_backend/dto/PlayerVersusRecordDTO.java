package kr.desponline.desp_backend.dto;

import lombok.Data;

@Data
public class PlayerVersusRecordDTO {
    private int score;
    private int victory;
    private int defeat;
    private int draw;

    private String winRate;
    private String tier;

    public PlayerVersusRecordDTO(int score, int victory, int defeat, int draw) {
        this.score = score;
        this.victory = victory;
        this.defeat = defeat;
        this.draw = draw;
        this.winRate = setWinRate(victory, defeat, draw);
        this.tier = setTierByScore(score);
    }

    public double calculateVictoryRate() {
        int totalMatches = victory + defeat + draw;
        return totalMatches > 0 ? (double) victory / totalMatches * 100 : 0;
    }

    public int calculateTotalMatches() {
        return victory + defeat + draw;
    }

    public String setWinRate(int victory, int defeat, int draw) {
        if (victory + defeat + draw == 0) {
            this.winRate = "0.0%";
        } else {
            this.winRate = String.format("%.1f%%", (double) victory / (victory + defeat + draw) * 100);
        }
        return winRate;
    }

    public String setTierByScore(int score) {
        if (score >= 0 && score <= 14) {
            return "Bronze";
        } else if (score >= 15 && score <= 29) {
            return "Silver";
        } else if (score >= 30 && score <= 44) {
            return "Gold";
        } else if (score >= 45 && score <= 59) {
            return "Diamond";
        } else if (score >= 60 && score <= 79) {
            return "Master";
        } else if (score >= 80) {
            return "Gladiator";
        } else {
            return "Unknown";
        }
    }
}
