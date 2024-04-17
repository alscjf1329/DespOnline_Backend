package kr.desponline.desp_backend.service.signin_session_service_test;


import static org.mockito.Mockito.when;

import java.util.UUID;
import kr.desponline.desp_backend.entity.webgamedb.GameUserEntity;
import kr.desponline.desp_backend.service.SigninSessionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

public class TestFindById {

    @Mock
    private RedisTemplate<String, GameUserEntity> redisTemplate;

    @Mock
    private ValueOperations<String, GameUserEntity> valueOperations;

    @InjectMocks
    private SigninSessionService mockSigninSessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testFindById() {
        // Given
        String sessionKey = UUID.randomUUID().toString();
        GameUserEntity expectedSession = GameUserEntity.createUser(
            "uuid", "nickname", "id", "pw");

        // Mock RedisTemplate behavior
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(sessionKey)).thenReturn(expectedSession);

        // When
        GameUserEntity actualSession = mockSigninSessionService.findById(sessionKey);

        // Then
        Assertions.assertThat(expectedSession).usingRecursiveComparison().isEqualTo(actualSession);
    }
}