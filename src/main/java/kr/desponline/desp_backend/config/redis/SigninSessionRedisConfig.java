package kr.desponline.desp_backend.config.redis;

import kr.desponline.desp_backend.entity.webgamedb.GameUserEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class SigninSessionRedisConfig extends RedisConfig {

    @Value("${spring.redis.signin.host}")
    private String host;

    @Value("${spring.redis.signin.port}")
    private int port;

    @Value("${spring.redis.signin.password}")
    private String password;


    @Bean
    public RedisConnectionFactory signinSessionRedisConnectionFactory() {
        return createLettuceConnectionFactory(host, port, 0, password);
    }

    @Bean
    public RedisTemplate<String, GameUserEntity> signinSessionRedisTemplate(
        @Qualifier("signinSessionRedisConnectionFactory") RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, GameUserEntity> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(GameUserEntity.class));
        return template;
    }
}