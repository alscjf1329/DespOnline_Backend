package kr.desponline.desp_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class MyConfiguration implements WebMvcConfigurer {

    @Value("${front.domain}")
    private String front;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(front) // 프론트엔드 도메인에서의 요청을 허용
            .allowedMethods("*") // http 모든 메소드 요청 허용
            .allowedHeaders("*") // 헤더 정보 모두 허용
            .allowCredentials(true);// 쿠키, 세션 정보도 허용
    }
}

