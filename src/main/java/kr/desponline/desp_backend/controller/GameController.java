package kr.desponline.desp_backend.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import kr.desponline.desp_backend.dto.GameInfoDTO;
import kr.desponline.desp_backend.entity.GameInfoEntity;
import kr.desponline.desp_backend.entity.GameInfoEntity.ProcessedGameInfoEntity;
import kr.desponline.desp_backend.service.GameInfoService;
import kr.desponline.desp_backend.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {

    @Value("${frontend.login-page-url}")
    private String loginEndpoint;

    @PostMapping("/main")
    public GameInfoDTO getGameInfo(
        @CookieValue(name = AuthController.SESSION_KEY_COOKIE_NAME, required = false, defaultValue = "") String sessionKey,
        HttpSession session,
        HttpServletResponse response,
        @Autowired TokenService tokenService,
        @Autowired GameInfoService gameInfoService) throws IOException {
        // sessionkey 확인
        if (tokenService.isNotValid(session, sessionKey)) {
            response.sendRedirect(loginEndpoint);
            return new GameInfoDTO("다시 토큰 인증을 해주세요.", null);
        }
        String nickname = (String) session.getAttribute(sessionKey);
        ProcessedGameInfoEntity gameInfo = gameInfoService.saveGameInfo(nickname);
        return new GameInfoDTO(null, gameInfo);
    }

    @PostMapping("/restart")
    public GameInfoDTO restartGame(
        @CookieValue(name = AuthController.SESSION_KEY_COOKIE_NAME, required = false, defaultValue = "") String sessionKey,
        HttpSession session,
        HttpServletResponse response,
        @Autowired TokenService tokenService,
        @Autowired GameInfoService gameInfoService) throws IOException {
        // sessionkey 확인
        if (tokenService.isNotValid(session, sessionKey)) {
            response.sendRedirect(loginEndpoint);
            return new GameInfoDTO("다시 토큰 인증을 해주세요.", null);
        }
        String nickname = (String) session.getAttribute(sessionKey);
        GameInfoEntity gameInfo = gameInfoService.insertNewGameInfoEntity(nickname);
        return new GameInfoDTO(null, gameInfo.toProcessedGameInfoEntity());
    }
}
