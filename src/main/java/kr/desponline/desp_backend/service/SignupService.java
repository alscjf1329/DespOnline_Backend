package kr.desponline.desp_backend.service;

import kr.desponline.desp_backend.dto.SignupRequestDTO;
import kr.desponline.desp_backend.entity.mysql.webgamedb.GameUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignupService {

    private final GameUserService gameUserService;
    private final SignUpValidateService signUpValidateService;
    private final EncodingService encodingService;

    @Autowired
    public SignupService(
        GameUserService gameUserService,
        SignUpValidateService signUpValidateService,
        EncodingService encodingService) {
        this.gameUserService = gameUserService;
        this.signUpValidateService = signUpValidateService;
        this.encodingService = encodingService;
    }

    public boolean signup(
        String uuid, String nickname, SignupRequestDTO signupDTO) {

        if (!signUpValidateService.validate(signupDTO.getId(), signupDTO.getPassword())) {
            return false;
        }
        String encodedPassword = encodingService.encode(signupDTO.getPassword());

        GameUserEntity gameUserEntity = GameUserEntity.createUser(
            uuid, nickname, signupDTO.getId(), encodedPassword);

        gameUserService.save(gameUserEntity);
        return true;
    }
}
