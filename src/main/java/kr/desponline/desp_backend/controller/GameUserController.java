package kr.desponline.desp_backend.controller;

import kr.desponline.desp_backend.dto.IDExistenceConfirmationDTO;
import kr.desponline.desp_backend.service.GameUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class GameUserController {

    private final GameUserService gameUserService;

    @Autowired
    public GameUserController(GameUserService gameUserService) {
        this.gameUserService = gameUserService;
    }

    @PostMapping("/exists/id")
    public ResponseEntity<?> confirmExistsId(
        @RequestBody IDExistenceConfirmationDTO idExistenceConfirmationDTO) {
        if (gameUserService.existsId(idExistenceConfirmationDTO.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.ok().build();
    }
}
