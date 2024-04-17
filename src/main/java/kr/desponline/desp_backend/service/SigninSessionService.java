package kr.desponline.desp_backend.service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import kr.desponline.desp_backend.entity.webgamedb.GameUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SigninSessionService {

    private static final long DEFAULT_TTL = TimeUnit.HOURS.toSeconds(1);

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

    public GameUserEntity findById(String sessionKey) {
        return redisTemplate.opsForValue().get(sessionKey);
    }
}
