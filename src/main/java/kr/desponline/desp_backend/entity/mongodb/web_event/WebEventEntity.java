package kr.desponline.desp_backend.entity.mongodb.web_event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;
import kr.desponline.desp_backend.dto.web_event.WebEventDTO;
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
@Document(collection = "WebEvent")
public class WebEventEntity {

    @Id
    @Field(value = "_id", targetType = FieldType.OBJECT_ID)
    private String id;

    @Field(value = "title")
    private String title;

    @Field(value = "description")
    private String description;

    @Field(value = "bannerUri")
    private String bannerUri;

    @Field(value = "startDate")
    private LocalDateTime startDate;

    @Field(value = "endDate")
    private LocalDateTime endDate;

    @Field(value = "type")
    private WebEventType type;

    @Field(value = "info")
    private Map<String, Object> info;

    public int getSize() {
        return (int) info.get("size");
    }

    public int getMaxOpportunity() {
        return (int) info.get("maxOpportunity");
    }

    public int getFlipCount() {
        return (int) info.get("flipCount");
    }

    public List<List<String>> getRewards() {
        return (List<List<String>>) info.get("rewards");
    }

    public WebEventDTO toWebEventDTO() {
        return new WebEventDTO(
            this.id,
            this.title,
            this.description,
            this.bannerUri,
            this.startDate,
            this.endDate,
            this.type,
            this.info
        );
    }

    public String getRandomReward(int rewardLevel) {
        List<String> rewardsInRewardLevel = getRewards().get(rewardLevel);

        // rewardsInRewardLevel 리스트가 비어있는지 확인
        if (rewardsInRewardLevel == null || rewardsInRewardLevel.isEmpty()) {
            return null; // 보상이 없는 경우 null 반환 또는 다른 처리 방법 선택
        }

        // Random 클래스를 사용하여 리스트에서 랜덤한 요소 선택
        Random random = new Random();
        int randomIndex = random.nextInt(rewardsInRewardLevel.size());
        return rewardsInRewardLevel.get(randomIndex);
    }
}
