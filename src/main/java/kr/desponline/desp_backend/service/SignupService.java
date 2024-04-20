package kr.desponline.desp_backend.service;

import jakarta.servlet.http.HttpServletResponse;
import kr.desponline.desp_backend.dto.AccessCredentialDTO;
import kr.desponline.desp_backend.dto.CertificationResultDTO;
import kr.desponline.desp_backend.dto.SignupRequestDTO;
import kr.desponline.desp_backend.entity.redis.signup.SignupSessionEntity;
import kr.desponline.desp_backend.entity.webgamedb.GameUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignupService {

    private final TokenService tokenService;
    private final GameUserService gameUserService;
    private final SignUpValidateService signUpValidateService;
    private final EncodingService encodingService;

    private final SignupSessionService signupSessionService;

    @Autowired
    public SignupService(TokenService tokenService,
        GameUserService gameUserService,
        SignUpValidateService signUpValidateService,
        EncodingService encodingService,
        SignupSessionService signupSessionService) {
        this.tokenService = tokenService;
        this.gameUserService = gameUserService;
        this.signUpValidateService = signUpValidateService;
        this.encodingService = encodingService;
        this.signupSessionService = signupSessionService;
    }

    public boolean signup(
        SignupSessionEntity signupSession, SignupRequestDTO signupDTO) {

        if (!signUpValidateService.validate(signupDTO.getId(), signupDTO.getPw())) {
            return false;
        }
        String encodedPassword = encodingService.encode(signupDTO.getPw());

        GameUserEntity gameUserEntity = GameUserEntity.createUser(
            signupSession.getUuid(), signupSession.getNickname(), signupDTO.getId(),
            encodedPassword);

        gameUserService.save(gameUserEntity);
        return true;
    }

    public CertificationResultDTO authenticateServerUser(
        AccessCredentialDTO accessCredentialDTO) {

        return tokenService.authenticate(
            accessCredentialDTO.getNickname(),
            accessCredentialDTO.getAuthenticationCode()
        );
    }

    public String addSession(
        CertificationResultDTO certificationResultDTO,
        AccessCredentialDTO accessCredentialDTO) {
        SignupSessionEntity signupSessionEntity = new SignupSessionEntity(
            certificationResultDTO.getUuid(),
            accessCredentialDTO.getNickname());
        return signupSessionService.save(signupSessionEntity);
    }

    public void deleteSession(final String sessionKey) {
        signupSessionService.delete(sessionKey);
    }

    public SignupSessionEntity findSession(final String sessionKey) {
        return signupSessionService.findById(sessionKey);
    }

    public void addSessionKeyCookie(final HttpServletResponse response, final String sessionKey) {
        response.addCookie(
            signupSessionService.createSessionKeyCookie(sessionKey));
    }
}
