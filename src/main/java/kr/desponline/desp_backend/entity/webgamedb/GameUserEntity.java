package kr.desponline.desp_backend.entity.webgamedb;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;

@Entity
@Getter
@Setter
@Qualifier(value = "webgamedbDataSource")
@Table(name = "user")
public class GameUserEntity {

    @Id
    private String uuid;
    private String id;
    private String pw;
    private Integer role;
    private Integer state;
    private LocalDateTime createdTime;
    private LocalDateTime last_longin_time;
}
