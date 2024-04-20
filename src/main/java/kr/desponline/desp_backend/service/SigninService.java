package kr.desponline.desp_backend.service;

import jakarta.servlet.http.HttpServletResponse;
import java.util.Objects;
import kr.desponline.desp_backend.dto.SigninRequestDTO;
import kr.desponline.desp_backend.entity.webgamedb.GameUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SigninService {

    private final GameUserService gameUserService;
    private final EncodingService encodingService;
    private final MinecraftPublicAPIService minecraftPublicAPIService;
    private final SigninSessionService signinSessionService;

    @Autowired
    public SigninService(
        GameUserService gameUserService,
        EncodingService encodingService,
        MinecraftPublicAPIService minecraftPublicAPIService,
        SigninSessionService signinSessionService) {
        this.gameUserService = gameUserService;
        this.encodingService = encodingService;
        this.minecraftPublicAPIService = minecraftPublicAPIService;
        this.signinSessionService = signinSessionService;
    }

    public GameUserEntity signin(
        final SigninRequestDTO signInRequestDTO) {

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
        return signinSessionService.save(gameUser);
    }

    public GameUserEntity findSession(final String sessionKey) {
        return signinSessionService.findById(sessionKey);
    }

    public void addSessionKeyCookie(final HttpServletResponse response, final String sessionKey) {
        response.addCookie(
            signinSessionService.createSessionKeyCookie(sessionKey));
    }
}
