package kr.desponline.desp_backend.entity.mysql.webgamedb;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
import lombok.ToString;

@Entity
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class GameUserEntity {

    @Id
    private String uuid;

    @Column(nullable = false)
    private String nickname;

    @JsonIgnore
    @Column(nullable = false, unique = true)
    private String id;

    @JsonIgnore
    @Column(name = "encoded_password", nullable = false)
    private String encodedPassword;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ROLE role;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private STATE state;

    @Column(name = "created_at", nullable = false)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private LocalDateTime createdAt;

    @Column(name = "last_login_at")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
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

    public void updateId(String newId) {
        this.id = newId;
    }

    public void updatePassword(String newEncodedPassword) {
        this.encodedPassword = newEncodedPassword;
    }

    public enum ROLE {
        ADMIN, STAFF, USER
    }

    public enum STATE {
        ACTIVATED, DEACTIVATED, BANNED, STOPPED, REMOVED
    }
}
