package kr.desponline.desp_backend.entity.mongodb;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "Reward")
public class RewardEntity {

    private String name;
    private Content content;

    @Getter
    public static class Content {

        private String serializedInventory;
        private Double money;
    }
}
