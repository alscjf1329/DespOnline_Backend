package kr.desponline.desp_backend.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.desponline.desp_backend.dto.CertificationResultDTO;
import kr.desponline.desp_backend.dto.SigninRequestDTO;
import kr.desponline.desp_backend.dto.SignupRequestDTO;
import kr.desponline.desp_backend.entity.mysql.webgamedb.GameUserEntity;
import kr.desponline.desp_backend.service.GameUserService;
import kr.desponline.desp_backend.service.SigninService;
import kr.desponline.desp_backend.service.SigninSessionService;
import kr.desponline.desp_backend.service.SignupService;
import kr.desponline.desp_backend.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sign")
public class SignController {

    private final SigninService signinService;
    private final SigninSessionService signinSessionService;
    private final SignupService signupService;
    private final GameUserService gameUserService;
    private final TokenService tokenService;



    @Autowired
    public SignController(SigninService signinService,
        SigninSessionService signinSessionService,
        SignupService signupService,
        GameUserService gameUserService,
        TokenService tokenService) {
        this.signinService = signinService;
        this.signinSessionService = signinSessionService;
        this.signupService = signupService;
        this.gameUserService = gameUserService;
        this.tokenService = tokenService;
    }

    @RequestMapping(value = "/in", method = {RequestMethod.POST, RequestMethod.GET})
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

    @GetMapping(value = "/out")
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

    @PostMapping("/up")
    public ResponseEntity<?> signup(
        @RequestBody SignupRequestDTO signupDTO) {
        CertificationResultDTO authenticateResult = tokenService.authenticate(
            signupDTO.getNickname(),
            signupDTO.getAuthenticationCode());
        if (!authenticateResult.isValid()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(authenticateResult);
        }

        if (gameUserService.existsByUuid(authenticateResult.getUuid())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("You already have an account.");
        }

        boolean signupSuccess = signupService.signup(authenticateResult.getUuid(),
            signupDTO.getNickname(), signupDTO);

        if (!signupSuccess) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Invalid format for ID or password.");
        }

        return ResponseEntity.ok().build();
    }
}
