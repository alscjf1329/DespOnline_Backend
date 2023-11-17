package kr.desponline.desp_backend.service;

import kr.desponline.desp_backend.dto.PlayerVersusDTO;
import kr.desponline.desp_backend.mongodb_repository.PlayerVersusRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PlayerVersusService {
    private final PlayerVersusRepository playerVersusRepository;

    public PlayerVersusService(PlayerVersusRepository playerVersusRepository) {
        this.playerVersusRepository = playerVersusRepository;
    }

    public List<PlayerVersusDTO> getTopScorePlayer(long limit) {
        List<PlayerVersusDTO> playerVersusDTOList = playerVersusRepository.findPvpOrderByScore().take(limit).collectList().block();
        Collections.sort(playerVersusDTOList);
        return playerVersusDTOList;
    }
}
