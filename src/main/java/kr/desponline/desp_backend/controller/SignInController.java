package kr.desponline.desp_backend.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.desponline.desp_backend.dto.SigninRequestDTO;
import kr.desponline.desp_backend.entity.mysql.webgamedb.GameUserEntity;
import kr.desponline.desp_backend.service.SigninService;
import kr.desponline.desp_backend.service.SigninSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signin")
public class SignInController {

    private final SigninService signinService;

    @Autowired
    public SignInController(SigninService signinService) {
        this.signinService = signinService;
    }

    @RequestMapping(value = "", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseEntity<Object> signIn(
        @RequestBody(required = false) SigninRequestDTO signInRequestDTO,
        HttpServletResponse response,
        @CookieValue(value = SigninSessionService.SESSION_KEY_COOKIE_NAME, required = false) Cookie sessionKeyCookie) {
        // 세션이 존재할 시 ok 보냄
        if (sessionKeyCookie != null) {
            GameUserEntity gameUser = signinService.findSession(sessionKeyCookie.getValue());
            if (gameUser != null) {
                return ResponseEntity.ok().body(gameUser);
            }
        }

        if (signInRequestDTO == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        GameUserEntity gameUser = signinService.signin(signInRequestDTO);
        if (gameUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String sessionKey = signinService.addSession(gameUser);
        signinService.addSessionKeyCookie(response, sessionKey);
        return ResponseEntity.ok().body(gameUser);
    }
}
