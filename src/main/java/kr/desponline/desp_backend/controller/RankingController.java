package kr.desponline.desp_backend.controller;

import java.util.List;
import kr.desponline.desp_backend.dto.RPGPlayerDTO;
import kr.desponline.desp_backend.service.RPGPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ranking")
public class RankingController {
    public static final long LIMIT = 100;
    @Autowired
    private RPGPlayerService rpgPlayerService;

    @GetMapping("/level")
    public List<RPGPlayerDTO> getTopPlayers() {
        return rpgPlayerService.getTopPlayers(LIMIT);
    }
}
