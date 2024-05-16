package kr.desponline.desp_backend.dto.web_event.cardFlipping;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import kr.desponline.desp_backend.entity.mongodb.web_event.WebEventEntity;
import kr.desponline.desp_backend.entity.mysql.webgamedb.CardFlippingUserEntity;
import kr.desponline.desp_backend.entity.mysql.webgamedb.GameUserEntity;
import kr.desponline.desp_backend.service.MySQLTypeConvertService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CardFlippingUserDTO {

    private Long id;

    private GameUserEntity user;

    private String eventId;

    private List<Integer> progress;

    @JsonIgnore
    private List<Integer> answer;

    private Integer remainingFlipCount; // 판당 남은 뒤집을 수 있는 횟수

    private Integer flipOpportunity; // 뒤집을 수 있는 횟수

    private Integer resetOpportunity;


    public static CardFlippingUserDTO createDefault(
        GameUserEntity user, WebEventEntity event,
        Function<Integer, List<Integer>> randomStrategy) {

        return new CardFlippingUserDTO(
            null,
            user,
            event.getId(),
            new ArrayList<>(Collections.nCopies(event.getSize(), null)),
            randomStrategy.apply(event.getSize()),
            event.getMaxOpportunity(),
            0,
            0
        );
    }

    public CardFlippingUserEntity toCardFlippingEntity() {
        MySQLTypeConvertService mySQLTypeConvertService = new MySQLTypeConvertService();
        return new CardFlippingUserEntity(
            this.id,
            this.user,
            this.eventId,
            mySQLTypeConvertService.listToString(this.progress),
            mySQLTypeConvertService.listToString(this.answer),
            this.remainingFlipCount,
            this.flipOpportunity,
            this.resetOpportunity
        );
    }

    public List<Integer> show(List<Integer> indexes) {
        return indexes.stream().map(index -> answer.get(index)).toList();
    }

    public FlipCardResultDTO flip(List<Integer> indexes) {
        List<Integer> showResult = indexes.stream().map(index -> answer.get(index)).toList();

        indexes.forEach(index -> {
            progress.set(index, answer.get(index));
        });

        this.flipOpportunity--;
        return new FlipCardResultDTO(allSame(showResult), showResult);
    }

    public void reset(WebEventEntity event,
        Function<Integer, List<Integer>> randomStrategy) {
        this.progress = new ArrayList<>(Collections.nCopies(event.getSize(), null));
        this.answer = randomStrategy.apply(event.getSize());
        this.remainingFlipCount = event.getMaxOpportunity();
    }

    public boolean checkFlipped(List<Integer> indexes) {
        return !indexes.stream()
            .filter(index -> this.progress.get(index) != null).toList()
            .isEmpty();
    }

    private boolean allSame(List<Integer> elements) {
        if (elements == null || elements.isEmpty()) {
            return false;
        }

        return elements.stream().allMatch(element -> element.equals(elements.get(0)));
    }
}
