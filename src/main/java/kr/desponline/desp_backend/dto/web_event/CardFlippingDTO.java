package kr.desponline.desp_backend.dto.web_event;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import kr.desponline.desp_backend.entity.mongodb.web_event.WebEventEntity;
import kr.desponline.desp_backend.entity.mysql.webgamedb.CardFlippingEntity;
import kr.desponline.desp_backend.entity.mysql.webgamedb.GameUserEntity;
import kr.desponline.desp_backend.service.MySQLTypeConvertService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CardFlippingDTO {

    private GameUserEntity user;

    private String eventId;

    private List<Integer> progress;

    @JsonIgnore
    private List<Integer> answer;

    private Integer flipOpportunity;

    private Integer resetOpportunity;


    public static CardFlippingDTO createDefault(
        GameUserEntity user, WebEventEntity event,
        Function<Integer, List<Integer>> randomStrategy) {

        int size = (int) event.getInfo().getOrDefault("size", 10);
        return new CardFlippingDTO(
            user,
            event.getId(),
            new ArrayList<>(Collections.nCopies(size, null)),
            randomStrategy.apply(size),
            0,
            0
        );
    }

    public CardFlippingEntity toCardFlippingEntity() {
        MySQLTypeConvertService mySQLTypeConvertService = new MySQLTypeConvertService();
        return new CardFlippingEntity(
            null,
            this.user,
            this.eventId,
            mySQLTypeConvertService.listToString(this.progress),
            mySQLTypeConvertService.listToString(this.answer),
            this.flipOpportunity,
            this.resetOpportunity
        );
    }
}
