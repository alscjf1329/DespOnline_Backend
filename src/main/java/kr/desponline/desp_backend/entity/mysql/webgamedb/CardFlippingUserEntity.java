package kr.desponline.desp_backend.entity.mysql.webgamedb;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import kr.desponline.desp_backend.dto.web_event.cardFlipping.CardFlippingUserDTO;
import kr.desponline.desp_backend.service.MySQLTypeConvertService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "card_flipping")
public class CardFlippingUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid")
    private GameUserEntity user;

    @Column(name = "event_id")
    private String eventId;

    @Column(name = "reward_level")
    private Integer rewardLevel;

    @Column(nullable = false)
    private String progress;

    @Column(nullable = false)
    private String answer;

    @Column(name = "remaining_flip_count", nullable = false)
    private Integer remainingFlipCount;

    @Column(name = "flip_opportunity", nullable = false)
    private Integer flipOpportunity;

    @Column(name = "reset_opportunity", nullable = false)
    private Integer resetOpportunity;

    public List<Integer> deserializeProgress() {
        return new MySQLTypeConvertService().stringToList(this.progress, Integer::parseInt);
    }

    public List<Integer> deserializeAnswer() {
        return new MySQLTypeConvertService().stringToList(this.answer, Integer::parseInt);
    }

    public CardFlippingUserDTO toCardFlippingDTO() {
        return new CardFlippingUserDTO(
            this.id,
            this.user,
            this.eventId,
            this.rewardLevel,
            deserializeProgress(),
            deserializeAnswer(),
            this.remainingFlipCount,
            this.flipOpportunity,
            this.resetOpportunity
        );
    }
}
