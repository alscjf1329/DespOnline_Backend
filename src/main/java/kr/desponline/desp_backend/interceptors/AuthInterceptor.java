package kr.desponline.desp_backend.interceptors;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Optional;
import kr.desponline.desp_backend.entity.webgamedb.GameUserEntity;
import kr.desponline.desp_backend.service.SigninSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    private static final String REDIRECT_ENDPOINT = "/signin";

    private final SigninSessionService signinSessionService;

    @Autowired
    AuthInterceptor(SigninSessionService signinSessionService) {
        this.signinSessionService = signinSessionService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            response.sendRedirect(REDIRECT_ENDPOINT);
            return false;
        }
        Optional<Cookie> signinSessionKeyCookie = Arrays.stream(cookies)
            .filter(
                cookie -> cookie.getName().equals(SigninSessionService.SESSION_KEY_COOKIE_NAME))
            .findFirst();

        if (signinSessionKeyCookie.isEmpty()) {
            response.sendRedirect(REDIRECT_ENDPOINT);
            return false;
        }

        GameUserEntity gameUser = signinSessionService.findById(
            signinSessionKeyCookie.get().getValue());

        if (gameUser == null) {
            response.sendRedirect(REDIRECT_ENDPOINT);
            return false;
        }
        return true;
    }

}
