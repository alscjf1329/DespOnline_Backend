package kr.desponline.desp_backend.service;

import kr.desponline.desp_backend.dto.PlayerRecordDTO;
import kr.desponline.desp_backend.entity.mongodb.PlayerEntity;
import kr.desponline.desp_backend.entity.mongodb.PlayerVersusEntity;
import kr.desponline.desp_backend.repository.mongodb.PlayerVersusRepository;
import kr.desponline.desp_backend.repository.mongodb.RPGSharpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    @Autowired
    private RPGSharpRepository rpgSharpRepository;
    @Autowired
    private PlayerVersusRepository playerVersusRepository;

    public PlayerRecordDTO findRecordByNickname(String nickname) {
        PlayerEntity player = rpgSharpRepository.findPlayerByNickname(nickname).block();
        if (player == null) {
            return null;
        }
        PlayerVersusEntity playerVersus = playerVersusRepository.findPvpByNickname(
            nickname).block();
        assert playerVersus != null;

        return new PlayerRecordDTO(player, playerVersus.getRecord());
    }

    public String findUuidByNickname(String nickname) {
        PlayerEntity player = rpgSharpRepository.findPlayerByNickname(nickname).block();
        if (player == null) {
            return null;
        }
        return player.getUuid();
    }
}
