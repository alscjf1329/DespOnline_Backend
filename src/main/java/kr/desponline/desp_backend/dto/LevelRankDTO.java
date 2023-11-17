package kr.desponline.desp_backend.dto;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "RPGPlayer")
public class LevelRankDTO {
    @Id
    private final String uuid;
    private final String nickname;
    private final String job;
    private final int level;
    private final double exp;
    private final double maxExp;
    private final double expPercent;
    private final String lastPlayTime;

    public LevelRankDTO(String uuid, String nickname, String job, int level, double exp, double maxExp,
                        String lastPlayTime) {
        this.uuid = uuid;
        this.nickname = nickname;
        this.job = job;
        this.level = level;
        this.exp = exp;
        this.maxExp = maxExp;
        this.expPercent = exp / maxExp * 100;
        this.lastPlayTime = lastPlayTime;
    }
}
