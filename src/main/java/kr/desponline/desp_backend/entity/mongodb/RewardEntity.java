package kr.desponline.desp_backend.entity.mongodb;

import kr.desponline.desp_backend.dto.MailDTO;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "Reward")
public class RewardEntity {

    private String name;
    private MailDTO mail;
}
