package kr.desponline.desp_backend.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.UUID;
import kr.desponline.desp_backend.dto.AccessCredentialDTO;
import kr.desponline.desp_backend.dto.CertificationResultDTO;
import kr.desponline.desp_backend.dto.SignupRequestDTO;
import kr.desponline.desp_backend.exception.ErrorCode;
import kr.desponline.desp_backend.exception.customs.SignupException;
import kr.desponline.desp_backend.service.SearchService;
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
    private final SearchService searchService;

    @Autowired
    public SignupController(TokenService tokenService, SearchService searchService) {
        this.tokenService = tokenService;
        this.searchService = searchService;
    }

    @PostMapping("")
    public CertificationResultDTO signup(
        @RequestBody SignupRequestDTO signupDTO, HttpSession session,
        @CookieValue(SESSION_KEY_COOKIE_NAME) String sessionKeyCookieValue,
        HttpServletResponse response) {
        UserInfo userInfo = (UserInfo) session.getAttribute(sessionKeyCookieValue);
        if (userInfo == null) {
            throw new SignupException("You are an unauthenticated server user.",
                ErrorCode.UNAUTHENTICATED_SERVER_USER);
        }
        /* TODO
         * 회원 가입 대상이 중복되는지 체크
         * id, pw가 형식에 맞는지 체크
         * */

        return null;
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
