package kr.desponline.desp_backend.service;


import java.util.UUID;
import java.util.concurrent.TimeUnit;
import kr.desponline.desp_backend.entity.mysql.webgamedb.GameUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class SigninSessionService {

    public static final String SESSION_KEY_COOKIE_NAME = "desp_login";

    private static final int DEFAULT_TTL = (int) TimeUnit.HOURS.toSeconds(1);

    private final RedisTemplate<String, GameUserEntity> redisTemplate;

    @Autowired
    public SigninSessionService(
        @Qualifier("signinSessionRedisTemplate") RedisTemplate<String, GameUserEntity> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String save(GameUserEntity gameUser, long ttl) {
        String randomKey = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(randomKey, gameUser, ttl, TimeUnit.SECONDS);
        return randomKey;
    }

    public String save(GameUserEntity gameUser) {
        return save(gameUser, DEFAULT_TTL);
    }

    public GameUserEntity findSession(String sessionKey) {
        return redisTemplate.opsForValue().get(sessionKey);
    }

    public ResponseCookie createSessionKeyCookie(final String sessionKey) {
        ResponseCookie cookie = ResponseCookie.from(SESSION_KEY_COOKIE_NAME, sessionKey)
            .httpOnly(true)
            .secure(true)
            .maxAge(DEFAULT_TTL)
            .build();
        return cookie;
    }

    public void removeSession(String sessionKey) {
        redisTemplate.delete(sessionKey);
    }
}
