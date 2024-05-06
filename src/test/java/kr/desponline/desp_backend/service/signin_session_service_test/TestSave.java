package kr.desponline.desp_backend.service.signin_session_service_test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.desponline.desp_backend.entity.mysql.webgamedb.GameUserEntity;
import kr.desponline.desp_backend.service.SigninSessionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;


@SpringBootTest
public class TestSave {

    @Autowired
    private SigninSessionService signinSessionService;
    @Autowired
    @Qualifier("signinSessionRedisTemplate")
    private RedisTemplate<String, GameUserEntity> redisTemplate;

    @Test
    public void testSave() throws JsonProcessingException {
        // Given
        GameUserEntity gameUser = GameUserEntity.createUser(
            "uuid", "nickname", "id", "pw");

        // json ignore를 적용시키기 위해 json으로 변환 후 다시 deserialize함
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(gameUser);
        GameUserEntity deserializedGameUser = objectMapper.readValue(json, GameUserEntity.class);

        // When
        String sessionId = signinSessionService.save(gameUser);

        // Then
        assertNotNull(sessionId);
        GameUserEntity actualSession = signinSessionService.findSession(sessionId);
        Assertions.assertThat(deserializedGameUser).usingRecursiveComparison()
            .isEqualTo(actualSession);
    }

    @Test
    public void testRemove() throws JsonProcessingException {
        // Given
        GameUserEntity gameUser = GameUserEntity.createUser(
            "uuid", "nickname", "id", "pw");

        // json ignore를 적용시키기 위해 json으로 변환 후 다시 deserialize함
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(gameUser);
        GameUserEntity deserializedGameUser = objectMapper.readValue(json, GameUserEntity.class);

        // When
        String sessionId = signinSessionService.save(gameUser);
        signinSessionService.removeSession(sessionId);

        // Then
        assertNotNull(sessionId);
        GameUserEntity actualSession = signinSessionService.findSession(sessionId);
        Assertions.assertThat(actualSession).isEqualTo(null);
    }
}
