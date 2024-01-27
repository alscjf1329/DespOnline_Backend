package kr.desponline.desp_backend.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.UUID;
import kr.desponline.desp_backend.dto.AccessCredentialDTO;
import kr.desponline.desp_backend.dto.CertificationResultDTO;
import kr.desponline.desp_backend.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/authenticate")
public class AuthController {

    public static final String SESSION_KEY_COOKIE_NAME = "desp-online-key";
    private final TokenService tokenService;

    @Autowired
    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/token")
    public CertificationResultDTO authenticateUser(
        @RequestBody AccessCredentialDTO accessCredentialDTO,
        HttpSession session, HttpServletResponse response) {

        CertificationResultDTO certificationResult = tokenService.authenticate(
            accessCredentialDTO.getNickname(),
            accessCredentialDTO.getAuthenticationCode()
        );

        if (!certificationResult.isValid()) {
            return new CertificationResultDTO(
                certificationResult.getRemainingAttempts(), false);
        }

        String sessionToken = UUID.randomUUID().toString();
        session.setAttribute(sessionToken, accessCredentialDTO.getNickname());

        Cookie cookie = new Cookie(SESSION_KEY_COOKIE_NAME, sessionToken);
        cookie.setHttpOnly(true); // XSS 공격 방지
        cookie.setSecure(true); // HTTPS를 통해서만 쿠키 전송
        cookie.setMaxAge(3600);

        response.addCookie(cookie);
        return new CertificationResultDTO(
            certificationResult.getRemainingAttempts(), certificationResult.isValid());
    }
}
