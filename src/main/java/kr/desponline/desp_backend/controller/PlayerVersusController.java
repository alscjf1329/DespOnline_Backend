package kr.desponline.desp_backend.controller;

import kr.desponline.desp_backend.dto.PlayerVersusDTO;
import kr.desponline.desp_backend.service.PlayerVersusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ranking")
public class PlayerVersusController {
    public static final long LIMIT = 100;
    @Autowired
    private PlayerVersusService playerVersusService;

    @GetMapping("/pvp")
    public List<PlayerVersusDTO> getTopScorePlayer() {
        return playerVersusService.getTopScorePlayer(LIMIT);
    }
}
