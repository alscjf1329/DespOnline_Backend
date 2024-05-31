package kr.desponline.desp_backend.dto.web_event.cardFlipping;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import kr.desponline.desp_backend.entity.mongodb.web_event.WebEventEntity;
import kr.desponline.desp_backend.entity.mongodb.web_event.details.CardFlippingDetail;
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

    private Integer rewardLevel;

    private List<Integer> progress;

    @JsonIgnore
    private List<Integer> answer;

    private Integer remainingFlipCount; // 판당 남은 뒤집을 수 있는 횟수

    private Integer flipOpportunity; // 뒤집을 수 있는 횟수

    private Integer resetOpportunity;


    public static CardFlippingUserDTO createDefault(
        GameUserEntity user, WebEventEntity event,
        Function<Integer, List<Integer>> randomStrategy) {
        CardFlippingDetail details = (CardFlippingDetail) event.getDetails();

        return new CardFlippingUserDTO(
            null,
            user,
            event.getId(),
            0,
            new ArrayList<>(Collections.nCopies(details.getSize(), null)),
            randomStrategy.apply(details.getSize()),
            details.getMaxOpportunity(),
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
            this.rewardLevel,
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

        if (allSame(showResult)) {
            indexes.forEach(index -> {
                progress.set(index, answer.get(index));
            });
            this.rewardLevel++;
        }
        this.remainingFlipCount--;
        this.flipOpportunity--;
        return new FlipCardResultDTO(
            allSame(showResult),
            showResult,
            flipOpportunity,
            rewardLevel,
            progress);
    }

    public ResetCardStatusResultDTO reset(WebEventEntity event,
        Function<Integer, List<Integer>> randomStrategy) {
        CardFlippingDetail details = (CardFlippingDetail) event.getDetails();
        this.rewardLevel = 0;
        this.progress = new ArrayList<>(Collections.nCopies(details.getSize(), null));
        this.answer = randomStrategy.apply(details.getSize());
        this.remainingFlipCount = details.getMaxOpportunity();
        this.resetOpportunity--;
        return new ResetCardStatusResultDTO(this.resetOpportunity, this.rewardLevel, this.progress);
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
