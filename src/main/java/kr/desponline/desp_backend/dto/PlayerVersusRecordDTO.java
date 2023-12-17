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
        if (score >= 100) {
            return "Gladiator"; // 100점 이상
        } else if (score >= 80) {
            return "Master"; // 80점 이상
        } else if (score >= 60) {
            return "Diamond"; // 60점 이상
        } else if (score >= 45) {
            return "Platinum"; // 45점 이상
        } else if (score >= 30) {
            return "Gold"; // 30점 이상
        } else if (score >= 15) {
            return "Silver"; // 15점 이상
        } else if (score >= 0) {
            return "Bronze"; // 0점 이상
        } else {
            return "Unknown"; // 음수 점수에 대한 처리
        }
    }
}
