package kr.desponline.desp_backend.entity.mongodb;

import lombok.Getter;
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
    private Object rpgMail;
}
