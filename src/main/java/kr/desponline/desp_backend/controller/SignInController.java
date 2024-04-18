package kr.desponline.desp_backend.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.desponline.desp_backend.dto.SigninRequestDTO;
import kr.desponline.desp_backend.entity.webgamedb.GameUserEntity;
import kr.desponline.desp_backend.service.SigninService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signin")
public class SignInController {

    private final SigninService signinService;

    @Autowired
    public SignInController(SigninService signinService) {
        this.signinService = signinService;
    }

    @PostMapping("")
    public ResponseEntity<Object> signIn(
        @RequestBody SigninRequestDTO signInRequestDTO,
        HttpServletResponse response,
        @CookieValue(value = SigninService.SESSION_KEY_COOKIE_NAME, required = false) Cookie sessionKeyCookie) {
        if (sessionKeyCookie != null) {
            return ResponseEntity.ok().build();
        }
        GameUserEntity gameUser = signinService.signin(signInRequestDTO);
        if (gameUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String sessionKey = signinService.addSession(gameUser);
        signinService.addSessionKeyCookie(response, sessionKey);
        return ResponseEntity.ok().build();
    }
}
