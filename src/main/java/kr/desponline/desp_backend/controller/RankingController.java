package kr.desponline.desp_backend.controller;

import java.util.List;
import kr.desponline.desp_backend.entity.mongodb.PlayerEntity;
import kr.desponline.desp_backend.entity.mongodb.PlayerVersusEntity;
import kr.desponline.desp_backend.service.PlayerVersusService;
import kr.desponline.desp_backend.service.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ranking")
public class RankingController {

    public static final long LIMIT = 100;
    @Autowired
    private RankingService rpgPlayerService;

    @Autowired
    private PlayerVersusService playerVersusService;

    @GetMapping("/pvp")
    public List<PlayerVersusEntity> getTopScorePlayer() {
        return playerVersusService.getTopScorePlayer(LIMIT);
    }

    @GetMapping("/level")
    public List<PlayerEntity> getTopPlayers() {
        return rpgPlayerService.readTopPlayers(LIMIT);
    }
}
