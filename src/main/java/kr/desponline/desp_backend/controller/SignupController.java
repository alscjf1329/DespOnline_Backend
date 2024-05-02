package kr.desponline.desp_backend.controller;

import kr.desponline.desp_backend.dto.CertificationResultDTO;
import kr.desponline.desp_backend.dto.SignupRequestDTO;
import kr.desponline.desp_backend.service.GameUserService;
import kr.desponline.desp_backend.service.SignupService;
import kr.desponline.desp_backend.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SignupController {

    private final SignupService signupService;
    private final GameUserService gameUserService;
    private final TokenService tokenService;

    @Autowired
    public SignupController(
        SignupService signupService,
        GameUserService gameUserService,
        TokenService tokenService) {
        this.signupService = signupService;
        this.gameUserService = gameUserService;
        this.tokenService = tokenService;
    }

    @PostMapping("")
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
