package kr.desponline.desp_backend.dto;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "PlayerVersus")
public class PlayerVersusDTO {
    @Id
    private String uuid;
    private PlayerVersusRecordDTO record;

    public PlayerVersusDTO(String uuid, PlayerVersusRecordDTO record) {
        this.uuid = uuid;
        this.record = record;
    }
}
