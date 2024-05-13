package kr.desponline.desp_backend.entity.mongodb.web_event;

import java.time.LocalDateTime;
import java.util.Map;
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

    @Field(value = "banner-uri")
    protected String bannerUri;

    @Field(value = "start-date")
    protected LocalDateTime startDate;

    @Field(value = "end-date")
    protected LocalDateTime endDate;

    @Field(value = "type")
    protected WebEventType type;

    @Field(value = "info")
    protected Map<String, Object> info;
}
