package kr.desponline.desp_backend.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import kr.desponline.desp_backend.entity.redis.signin.SigninSessionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SigninSessionService {

    private static final long DEFAULT_TTL = TimeUnit.MINUTES.toSeconds(3);

    private final RedisTemplate<String, SigninSessionEntity> redisTemplate;

    @Autowired
    public SigninSessionService(
        @Qualifier("signinSessionRedisTemplate") RedisTemplate<String, SigninSessionEntity> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String save(SigninSessionEntity signinSessionEntity, long ttl) {
        String randomKey = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(randomKey, signinSessionEntity, ttl, TimeUnit.SECONDS);
        return randomKey;
    }

    public String save(SigninSessionEntity signinSessionEntity) {
        return save(signinSessionEntity, DEFAULT_TTL);
    }

    public SigninSessionEntity findById(String sessionKey) {
        return this.redisTemplate.opsForValue().get(sessionKey);
    }
}
