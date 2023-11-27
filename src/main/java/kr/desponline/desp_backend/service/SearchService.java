package kr.desponline.desp_backend.service;

import kr.desponline.desp_backend.dto.PlayerDTO;
import kr.desponline.desp_backend.dto.PlayerRecordDTO;
import kr.desponline.desp_backend.dto.PlayerVersusDTO;
import kr.desponline.desp_backend.dto.PlayerVersusRecordDTO;
import kr.desponline.desp_backend.mongodb_repository.PlayerVersusRepository;
import kr.desponline.desp_backend.mongodb_repository.RPGSharpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
    @Autowired
    private RPGSharpRepository rpgSharpRepository;
    @Autowired
    private PlayerVersusRepository playerVersusRepository;

    public PlayerRecordDTO findRecordByNickname(String nickname) {
        PlayerDTO player = rpgSharpRepository.findPlayerByNickname(nickname).block();
        if (player == null) {
            return null;
        }
        PlayerVersusDTO playerVersus = playerVersusRepository.findPvpByNickname(
                nickname).block();
        assert playerVersus != null;

        return new PlayerRecordDTO(player, playerVersus.getRecord());
    }
}
