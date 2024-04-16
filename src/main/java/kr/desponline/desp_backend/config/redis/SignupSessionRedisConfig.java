package kr.desponline.desp_backend.config.redis;

import kr.desponline.desp_backend.entity.redis.signup.SignupSessionEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class SignupSessionRedisConfig extends RedisConfig {

    @Value("${spring.redis.signup.host}")
    private String host;

    @Value("${spring.redis.signup.port}")
    private int port;

    @Value("${spring.redis.signup.password}")
    private String password;

    @Bean
    @Primary
    public RedisConnectionFactory signupSessionRedisConnectionFactory() {
        return createLettuceConnectionFactory(host, port, 0, password);
    }

    @Bean
    public RedisTemplate<String, SignupSessionEntity> signupSessionRedisTemplate(
        @Qualifier("signupSessionRedisConnectionFactory") RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, SignupSessionEntity> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(SignupSessionEntity.class));
        return template;
    }
}