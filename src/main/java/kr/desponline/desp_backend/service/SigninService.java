package kr.desponline.desp_backend.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Objects;
import java.util.UUID;
import kr.desponline.desp_backend.entity.webgamedb.GameUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SigninService {

    private static final String SESSION_KEY_COOKIE_NAME = "desp_login";

    private final GameUserService gameUserService;
    private final EncodingService encodingService;
    private final MinecraftPublicAPIService minecraftPublicAPIService;
    private final HttpSession session;

    @Autowired
    public SigninService(GameUserService gameUserService, EncodingService encodingService,
        MinecraftPublicAPIService minecraftPublicAPIService, HttpSession session) {
        this.gameUserService = gameUserService;
        this.encodingService = encodingService;
        this.minecraftPublicAPIService = minecraftPublicAPIService;
        this.session = session;
    }

    public GameUserEntity signin(
        final kr.desponline.desp_backend.dto.SigninRequestDTO signInRequestDTO) {

        GameUserEntity gameUser = gameUserService.findGameUserEntityById(
            signInRequestDTO.getId());
        if (gameUser == null) {
            return null;
        }

        if (!encodingService.matches(signInRequestDTO.getPassword(),
            Objects.requireNonNull(gameUser).getEncodedPassword())) {
            return null;
        }
        // nickname 갱신
        String nickname = minecraftPublicAPIService.getNicknameByUuid(
            gameUser.getUuid()).getNickname();
        gameUser.updateNickname(nickname);

        // 마지막 로그인 시간 초기화
        gameUser.updateLastLoginAt();
        // 직접 gameUser를 저장함
        gameUserService.save(gameUser);
        return gameUser;
    }

    public String addSession(final GameUserEntity gameUser) {
        String sessionKey = UUID.randomUUID().toString();
        session.setAttribute(sessionKey, gameUser);
        return sessionKey;
    }

    public void addSessionKeyCookie(final HttpServletResponse response, final String sessionKey) {
        Cookie cookie = new Cookie(SESSION_KEY_COOKIE_NAME, sessionKey);
        cookie.setHttpOnly(true); // XSS 공격 방지
        cookie.setPath("/signin");
        cookie.setMaxAge(180);

        response.addCookie(cookie);
    }
}
