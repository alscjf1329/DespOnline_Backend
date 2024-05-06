package kr.desponline.desp_backend.config;

import kr.desponline.desp_backend.interceptors.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {

    private final AuthInterceptor authInterceptor;

    public WebMVCConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
            .excludePathPatterns("/**")
            .addPathPatterns("/event/**")
            .excludePathPatterns("/event");
    }
}
