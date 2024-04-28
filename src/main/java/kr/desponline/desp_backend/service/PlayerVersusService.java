package kr.desponline.desp_backend.service;

import java.util.Collections;
import java.util.List;
import kr.desponline.desp_backend.entity.mongodb.PlayerVersusEntity;
import kr.desponline.desp_backend.mongodb_repository.PlayerVersusRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerVersusService {

    private final PlayerVersusRepository playerVersusRepository;

    public PlayerVersusService(PlayerVersusRepository playerVersusRepository) {
        this.playerVersusRepository = playerVersusRepository;
    }

    public List<PlayerVersusEntity> getTopScorePlayer(long limit) {
        List<PlayerVersusEntity> playerVersusDTOList = playerVersusRepository.findPvpOrderByScore()
            .take(limit).collectList().block();
        Collections.sort(playerVersusDTOList);
        return playerVersusDTOList;
    }
}
