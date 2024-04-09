package kr.desponline.desp_backend.entity.webgamedb;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Column(name = "encoded_password")
    private String encodedPassword;

    @Enumerated(EnumType.STRING)
    private ROLE role;

    @Enumerated(EnumType.STRING)
    private STATE state;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    public static GameUserEntity createUser(String uuid, String id, String encodedPassword) {
        return new GameUserEntity(uuid, id, encodedPassword, ROLE.USER, STATE.ACTIVATED,
            LocalDateTime.now(), null);
    }

    public enum ROLE {
        ADMIN, STAFF, USER
    }

    public enum STATE {
        ACTIVATED, DEACTIVATED, BANNED, STOPPED, REMOVED
    }
}
