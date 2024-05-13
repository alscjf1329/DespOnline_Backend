package kr.desponline.desp_backend.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.desponline.desp_backend.service.SigninSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signout")
public class SignOutController {

    private final SigninSessionService signinSessionService;

    @Autowired
    public SignOutController(SigninSessionService signinSessionService) {
        this.signinSessionService = signinSessionService;
    }

    @GetMapping(value = "")
    public ResponseEntity<Object> signOut(
        HttpServletResponse response,
        @CookieValue(name = SigninSessionService.SESSION_KEY_COOKIE_NAME) String sessionKey) {
        if (sessionKey != null) {
            signinSessionService.removeSession(sessionKey);
        }

        Cookie sessionKeyCookie = new Cookie(SigninSessionService.SESSION_KEY_COOKIE_NAME, null);
        sessionKeyCookie.setMaxAge(0);
        sessionKeyCookie.setPath("/");
        response.addCookie(sessionKeyCookie);

        return ResponseEntity.ok().build();
    }
}
