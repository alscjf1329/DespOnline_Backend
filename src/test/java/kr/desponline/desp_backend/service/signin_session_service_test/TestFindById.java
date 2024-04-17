package kr.desponline.desp_backend.service.signin_session_service_test;


import static org.mockito.Mockito.when;

import java.util.UUID;
import kr.desponline.desp_backend.entity.redis.signin.SigninSessionEntity;
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
    private RedisTemplate<String, SigninSessionEntity> redisTemplate;

    @Mock
    private ValueOperations<String, SigninSessionEntity> valueOperations;

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
        SigninSessionEntity expectedSession = new SigninSessionEntity("uuid", "nickname");

        // Mock RedisTemplate behavior
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(sessionKey)).thenReturn(expectedSession);

        // When
        SigninSessionEntity actualSession = mockSigninSessionService.findById(sessionKey);

        // Then
        Assertions.assertThat(expectedSession).usingRecursiveComparison().isEqualTo(actualSession);
    }
}