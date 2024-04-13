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
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@Table(name = "user")
public class GameUserEntity {

    @Id
    private String uuid;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String id;

    @Column(name = "encoded_password", nullable = false)
    private String encodedPassword;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ROLE role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private STATE state;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    public static GameUserEntity createUser(String uuid, String nickname, String id,
        String encodedPassword) {
        return new GameUserEntity(uuid, nickname, id, encodedPassword, ROLE.USER, STATE.ACTIVATED,
            LocalDateTime.now(), null);
    }

    public void updateLastLoginAt() {
        this.lastLoginAt = LocalDateTime.now();
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public enum ROLE {
        ADMIN, STAFF, USER
    }

    public enum STATE {
        ACTIVATED, DEACTIVATED, BANNED, STOPPED, REMOVED
    }
}
