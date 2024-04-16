package kr.desponline.desp_backend.config.redis;

import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

public class RedisConfig {

    public RedisConnectionFactory createLettuceConnectionFactory(String host, int port, int dbIndex,
        String password) {
        final RedisStandaloneConfiguration redisStandaloneConfiguration
            = new RedisStandaloneConfiguration(host, port);
        redisStandaloneConfiguration.setDatabase(dbIndex);
        redisStandaloneConfiguration.setPassword(password);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }
}
