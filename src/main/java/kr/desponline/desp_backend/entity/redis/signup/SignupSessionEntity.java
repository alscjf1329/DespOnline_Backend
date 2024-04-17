package kr.desponline.desp_backend.entity.redis.signup;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SignupSessionEntity {

    private String uuid;

    private String nickname;
}
