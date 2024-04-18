package kr.desponline.desp_backend.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.desponline.desp_backend.dto.AccessCredentialDTO;
import kr.desponline.desp_backend.dto.CertificationResultDTO;
import kr.desponline.desp_backend.dto.SignupRequestDTO;
import kr.desponline.desp_backend.entity.redis.signup.SignupSessionEntity;
import kr.desponline.desp_backend.service.GameUserService;
import kr.desponline.desp_backend.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SignupController {

    private final SignupService signupService;
    private final GameUserService gameUserService;

    @Autowired
    public SignupController(
        SignupService signupService,
        GameUserService gameUserService) {
        this.signupService = signupService;
        this.gameUserService = gameUserService;
    }

    @PostMapping("")
    public ResponseEntity<?> signup(
        @RequestBody SignupRequestDTO signupDTO,
        @CookieValue(value = SignupService.SESSION_KEY_COOKIE_NAME, required = false) Cookie sessionKeyCookie) {
        if (sessionKeyCookie == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("You are an unauthenticated server user.");
        }
        String sessionKey = sessionKeyCookie.getValue();
        SignupSessionEntity signupSession = signupService.findSession(sessionKey);

        if (signupSession == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("You are an unauthenticated server user.");
        }

        if (gameUserService.existsByUuid(signupSession.getUuid())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("You already have an account.");
        }

        boolean signupSuccess = signupService.signup(signupSession, signupDTO);
        if (!signupSuccess) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Invalid format for ID or password.");
        }

        signupService.deleteSession(sessionKey);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> authenticateServerUser(
        @RequestBody AccessCredentialDTO accessCredentialDTO, HttpServletResponse response) {
        CertificationResultDTO certificationResultDTO = signupService.authenticateServerUser(
            accessCredentialDTO);

        if (!certificationResultDTO.isValid()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(certificationResultDTO);
        }

        String sessionKey = signupService.addSession(certificationResultDTO, accessCredentialDTO);

        signupService.addSessionKeyCookie(response, sessionKey);
        return ResponseEntity.ok().build();
    }
}
