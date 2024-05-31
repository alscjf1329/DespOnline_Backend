package kr.desponline.desp_backend.entity.mongodb;


import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "RewardMailBox")
public class RewardMailBoxEntity {

    @Id
    @Field(value = "_id", targetType = FieldType.OBJECT_ID)
    private String id;
    private String uuid;
    private List<Mail> rewardMails;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Mail {

        private String senderName;
        private String letter;
        private String serializedInventory;
        private Double money;
    }

    public static RewardMailBoxEntity createDefault(String uuid) {
        return new RewardMailBoxEntity(
            null,
            uuid,
            new ArrayList<>()
        );
    }

    public void addMail(Mail mail) {
        rewardMails.add(mail);
    }
}
