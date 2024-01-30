package kr.desponline.desp_backend.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {

    @Value("frontend.login-page-url")
    private String loginEndpoint;

    @PostMapping("/main")
    public boolean getGameInfo(
        @CookieValue(name = AuthController.SESSION_KEY_COOKIE_NAME, required = false, defaultValue = "") String sessionKey,
        HttpSession session,
        HttpServletResponse response) throws IOException {
        if (sessionKey.isEmpty() || session.getAttribute(sessionKey) == null) {
            response.sendRedirect(loginEndpoint);
            return false;
        }
        return true;
    }


}
