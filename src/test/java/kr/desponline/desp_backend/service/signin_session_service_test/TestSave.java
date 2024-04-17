package kr.desponline.desp_backend.service.signin_session_service_test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import kr.desponline.desp_backend.entity.redis.signin.SigninSessionEntity;
import kr.desponline.desp_backend.service.SigninSessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestSave {

    @Autowired
    private SigninSessionService signinSessionService;

    @Test
    public void testSave() {
        // Given
        SigninSessionEntity sessionEntity = new SigninSessionEntity("uuid", "nickname");

        // When
        String sessionId = signinSessionService.save(sessionEntity);

        // Then
        assertNotNull(sessionId);
        SigninSessionEntity actualSession = signinSessionService.findById(sessionId);
        assertNotNull(actualSession);
    }
}
