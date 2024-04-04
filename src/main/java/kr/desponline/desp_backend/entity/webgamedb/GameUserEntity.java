package kr.desponline.desp_backend.entity.webgamedb;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Qualifier;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Qualifier(value = "webgamedbDataSource")
@Table(name = "user")
public class GameUserEntity {

    @Id
    private String uuid;
    private String id;
    private String encoded_password;
    private Integer role;
    private Integer state;
    private LocalDateTime createdTime;
    private LocalDateTime last_longin_time;

    public static GameUserEntity createUser(String uuid, String id, String encodedPassword) {
        return new GameUserEntity(uuid, id, encodedPassword, 2, 1,
            LocalDateTime.now(), null);
    }
}
