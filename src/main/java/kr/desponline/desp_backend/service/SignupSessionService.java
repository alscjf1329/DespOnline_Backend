package kr.desponline.desp_backend.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import kr.desponline.desp_backend.entity.redis.signup.SignupSessionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SignupSessionService {

    private static final long DEFAULT_TTL = TimeUnit.MINUTES.toSeconds(3);

    private final RedisTemplate<String, SignupSessionEntity> redisTemplate;

    @Autowired
    public SignupSessionService(
        @Qualifier("signupSessionRedisTemplate") RedisTemplate<String, SignupSessionEntity> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public String save(SignupSessionEntity signupSessionEntity, long ttl) {
        String randomKey = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(randomKey, signupSessionEntity, ttl, TimeUnit.SECONDS);
        return randomKey;
    }

    public String save(SignupSessionEntity signupSessionEntity) {
        return save(signupSessionEntity, DEFAULT_TTL);
    }

    public SignupSessionEntity findById(String sessionKey) {
        return this.redisTemplate.opsForValue().get(sessionKey);
    }
}
