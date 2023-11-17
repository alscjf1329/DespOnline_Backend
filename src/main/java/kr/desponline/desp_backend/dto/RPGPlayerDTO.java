package kr.desponline.desp_backend.dto;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "RPGPlayer")
public class RPGPlayerDTO {
    @Id
    private final String id;
    private final String uuid;
    private final String nickname;
    private final int level;
    private final double exp;

    public RPGPlayerDTO(String id, String uuid, String nickname, int level, double exp) {
        this.id = id;
        this.uuid = uuid;
        this.nickname = nickname;
        this.level = level;
        this.exp = exp;
    }
}
