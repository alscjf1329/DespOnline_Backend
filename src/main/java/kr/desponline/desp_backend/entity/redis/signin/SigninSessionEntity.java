package kr.desponline.desp_backend.entity.redis.signin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SigninSessionEntity {

    private String uuid;

    private String nickname;
}
