package kr.desponline.desp_backend.service.signup_session_service_test;


import static org.mockito.Mockito.when;

import java.util.UUID;
import kr.desponline.desp_backend.entity.redis.signup.SignupSessionEntity;
import kr.desponline.desp_backend.service.SignupSessionService;
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
    private RedisTemplate<String, SignupSessionEntity> redisTemplate;

    @Mock
    private ValueOperations<String, SignupSessionEntity> valueOperations;

    @InjectMocks
    private SignupSessionService mockSignupSessionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testFindById() {
        // Given
        String sessionKey = UUID.randomUUID().toString();
        SignupSessionEntity expectedSession = new SignupSessionEntity("uuid", "nickname");

        // Mock RedisTemplate behavior
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(sessionKey)).thenReturn(expectedSession);

        // When
        SignupSessionEntity actualSession = mockSignupSessionService.findById(sessionKey);

        // Then
        Assertions.assertThat(expectedSession).usingRecursiveComparison().isEqualTo(actualSession);
    }
}