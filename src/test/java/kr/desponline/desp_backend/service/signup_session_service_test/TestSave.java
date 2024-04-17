package kr.desponline.desp_backend.service.signup_session_service_test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import kr.desponline.desp_backend.entity.redis.signup.SignupSessionEntity;
import kr.desponline.desp_backend.service.SignupSessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestSave {

    @Autowired
    private SignupSessionService signupSessionService;

    @Test
    public void testSave() {
        // Given
        SignupSessionEntity sessionEntity = new SignupSessionEntity("uuid", "nickname");

        // When
        String sessionId = signupSessionService.save(sessionEntity);

        // Then
        assertNotNull(sessionId);
        SignupSessionEntity actualSession = signupSessionService.findById(sessionId);
        assertNotNull(actualSession);
    }
}
