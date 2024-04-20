package kr.desponline.desp_backend.service;

import jakarta.servlet.http.Cookie;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import kr.desponline.desp_backend.entity.redis.signup.SignupSessionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SignupSessionService {

    public static final String SESSION_KEY_COOKIE_NAME = "desp_login_confirm";

    private static final int DEFAULT_TTL = (int) TimeUnit.MINUTES.toSeconds(3);

    private final RedisTemplate<String, SignupSessionEntity> redisTemplate;

    @Autowired
    public SignupSessionService(
        @Qualifier("signupSessionRedisTemplate") RedisTemplate<String, SignupSessionEntity> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String save(final SignupSessionEntity signupSessionEntity, final long ttl) {
        String randomKey = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(randomKey, signupSessionEntity, ttl, TimeUnit.SECONDS);
        return randomKey;
    }

    public String save(final SignupSessionEntity signupSessionEntity) {
        return save(signupSessionEntity, DEFAULT_TTL);
    }

    public SignupSessionEntity findById(final String sessionKey) {
        return this.redisTemplate.opsForValue().get(sessionKey);
    }

    public Boolean delete(final String sessionKey) {
        return this.redisTemplate.delete(sessionKey);
    }

    public Cookie createSessionKeyCookie(final String sessionKey) {
        Cookie cookie = new Cookie(SESSION_KEY_COOKIE_NAME, sessionKey);
        cookie.setHttpOnly(true); // XSS 공격 방지
        cookie.setPath("/");
        cookie.setMaxAge(DEFAULT_TTL);
        return cookie;
    }
}
