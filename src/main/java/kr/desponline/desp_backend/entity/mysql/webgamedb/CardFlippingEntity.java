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
import kr.desponline.desp_backend.dto.web_event.CardFlippingDTO;
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
public class CardFlippingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_uuid", referencedColumnName = "uuid")
    private GameUserEntity user;

    @Column(name = "event_id")
    private String eventId;

    @Column(nullable = false)
    private String progress;

    @Column(nullable = false)
    private String answer;

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

    public CardFlippingDTO toCardFlippingDTO() {
        return new CardFlippingDTO(
            this.user,
            this.eventId,
            deserializeProgress(),
            deserializeAnswer(),
            this.flipOpportunity,
            this.resetOpportunity
        );
    }
}
