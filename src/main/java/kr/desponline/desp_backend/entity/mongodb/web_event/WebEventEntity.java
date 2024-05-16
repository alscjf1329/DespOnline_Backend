package kr.desponline.desp_backend.entity.mongodb.web_event;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
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
    protected String id;

    @Field(value = "title")
    protected String title;

    @Field(value = "description")
    protected String description;

    @Field(value = "bannerUri")
    protected String bannerUri;

    @Field(value = "startDate")
    protected LocalDateTime startDate;

    @Field(value = "endDate")
    protected LocalDateTime endDate;

    @Field(value = "type")
    protected WebEventType type;

    @Field(value = "info")
    protected Map<String, Object> info;

    public int getSize() {
        return (int) info.get("size");
    }

    public int getMaxOpportunity() {
        return (int) info.get("maxOpportunity");
    }

    public int getFlipCount() {
        return (int) info.get("flipCount");
    }

    public List<String> getRewards() {
        return (List<String>) info.get("rewards");
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
}
