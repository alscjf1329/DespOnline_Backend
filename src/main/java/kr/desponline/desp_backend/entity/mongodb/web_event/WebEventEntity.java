package kr.desponline.desp_backend.entity.mongodb.web_event;

import java.time.LocalDateTime;
import java.util.Map;
import kr.desponline.desp_backend.dto.web_event.WebEventDTO;
import kr.desponline.desp_backend.entity.mongodb.web_event.details.WebEventDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "WebEvent")
public class WebEventEntity {

    @Id
    @Getter
    @Field(value = "_id", targetType = FieldType.OBJECT_ID)
    private String id;

    @Getter
    @Field(value = "title")
    private String title;

    @Getter
    @Field(value = "description")
    private String description;

    @Getter
    @Field(value = "bannerUri")
    private String bannerUri;

    @Getter
    @Field(value = "startDate")
    private LocalDateTime startDate;

    @Getter
    @Field(value = "endDate")
    private LocalDateTime endDate;

    @Getter
    @Field(value = "type")
    private WebEventType type;

    @Field(value = "details")
    private Map<String, Object> details;

    public WebEventDTO toWebEventDTO() {
        return new WebEventDTO(
            this.id,
            this.title,
            this.description,
            this.bannerUri,
            this.startDate,
            this.endDate,
            this.type,
            this.details
        );
    }

    public WebEventDetail getDetails() {
        return new WebEventDetail(this.details);
    }
}
