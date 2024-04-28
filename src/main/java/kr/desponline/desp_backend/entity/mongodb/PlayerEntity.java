package kr.desponline.desp_backend.entity.mongodb;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "RPGPlayer")
public class PlayerEntity {

    private final String uuid;
    private final String nickname;
    private final String job;
    private final int level;
    private final double exp;
    private final double maxExp;
    private final double expPercent;
    private final String lastPlayTime;

    public PlayerEntity(String uuid, String nickname, String job, int level, double exp,
        double maxExp,
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
