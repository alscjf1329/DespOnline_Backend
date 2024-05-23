package kr.desponline.desp_backend.entity.mongodb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import kr.desponline.desp_backend.dto.MailDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Getter
@Document(collection = "RPGPlayer")
public class RPGPlayerEntity {

    @Id
    @Field(value = "_id", targetType = FieldType.OBJECT_ID)
    protected String id;
    private String uuid;
    private String defaultName;
    private String nickname;
    private String job;
    private String serializeInventory;
    private Long level;
    private Double exp;
    private Double maxExp;
    private Long statPoint;
    private String lastPlayTime;
    private Object stat;
    private Object equipment;
    private Object friend;
    private Object playerQuest;
    private Object characterLocation;
    private Object rpgBuff;
    private Object rpgTitle;
    private RPGMail rpgMail;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RPGMail {

        private List<MailDTO> mails;
    }

    public void addMail(MailDTO... mails) {
        if (this.rpgMail == null) {
            this.rpgMail = new RPGMail(new ArrayList<>());
        }
        this.rpgMail.mails.addAll(Arrays.stream(mails).toList());
    }
}
