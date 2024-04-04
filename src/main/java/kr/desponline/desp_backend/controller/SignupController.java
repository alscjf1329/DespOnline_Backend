package kr.desponline.desp_backend.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.UUID;
import kr.desponline.desp_backend.dto.AccessCredentialDTO;
import kr.desponline.desp_backend.dto.CertificationResultDTO;
import kr.desponline.desp_backend.dto.SignupRequestDTO;
import kr.desponline.desp_backend.entity.webgamedb.GameUserEntity;
import kr.desponline.desp_backend.exception.ErrorCode;
import kr.desponline.desp_backend.exception.customs.SignupException;
import kr.desponline.desp_backend.service.EncodingService;
import kr.desponline.desp_backend.service.GameUserService;
import kr.desponline.desp_backend.service.SignUpValidateService;
import kr.desponline.desp_backend.service.TokenService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SignupController {

    public static final String SESSION_KEY_COOKIE_NAME = "desp_access_token";
    private final TokenService tokenService;
    private final GameUserService gameUserService;
    private final SignUpValidateService signUpValidateService;
    private final EncodingService encodingService;

    @Autowired
    public SignupController(TokenService tokenService,
        GameUserService gameUserService,
        SignUpValidateService signUpValidateService,
        EncodingService encodingService) {
        this.tokenService = tokenService;
        this.gameUserService = gameUserService;
        this.signUpValidateService = signUpValidateService;
        this.encodingService = encodingService;
    }

    @PostMapping("")
    public boolean signup(
        @RequestBody SignupRequestDTO signupDTO, HttpSession session,
        @CookieValue(SESSION_KEY_COOKIE_NAME) String sessionKeyCookieValue) {
        UserInfo userInfo = (UserInfo) session.getAttribute(sessionKeyCookieValue);
        if (userInfo == null) {
            throw new SignupException("You are an unauthenticated server user.",
                ErrorCode.UNAUTHENTICATED_SERVER_USER);
        }

        if (gameUserService.existsByUuid(userInfo.getUuid())) {
            throw new SignupException("You already have an account.",
                ErrorCode.DUPLICATE_REGISTRATION);
        }

        if (!signUpValidateService.validate(signupDTO.getId(), signupDTO.getPw())) {
            throw new SignupException("Invalid format for ID or password.",
                ErrorCode.INVALID_FORMAT_FOR_ID_OR_PASSWORD);
        }
        String encodedPassword = encodingService.encode(signupDTO.getPw());

        GameUserEntity gameUserEntity = GameUserEntity.createUser(
            userInfo.getUuid(), signupDTO.getId(), encodedPassword);

        gameUserService.save(gameUserEntity);
        return true;
    }

    @PostMapping("/confirm")
    public CertificationResultDTO authenticateServerUser(
        @RequestBody AccessCredentialDTO accessCredentialDTO,
        HttpSession session, HttpServletResponse response) {

        CertificationResultDTO certificationResult = tokenService.authenticate(
            accessCredentialDTO.getNickname(),
            accessCredentialDTO.getAuthenticationCode()
        );

        if (!certificationResult.isValid()) {
            return certificationResult;
        }

        // 무작위 session key(uuid) - UserInfo
        String sessionToken = UUID.randomUUID().toString();
        UserInfo userInfo = new UserInfo(
            certificationResult.getUuid(),
            accessCredentialDTO.getNickname());
        session.setAttribute(sessionToken, userInfo);

        Cookie cookie = new Cookie(SESSION_KEY_COOKIE_NAME, sessionToken);
        cookie.setHttpOnly(true); // XSS 공격 방지
        cookie.setSecure(true); // HTTPS를 통해서만 쿠키 전송
        cookie.setMaxAge(180);

        response.addCookie(cookie);
        return certificationResult;
    }

    @Getter
    @AllArgsConstructor
    public static class UserInfo {

        private final String uuid;
        private final String nickname;
    }
}
