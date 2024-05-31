package kr.desponline.desp_backend.service;

import java.util.List;
import kr.desponline.desp_backend.entity.mongodb.PlayerEntity;
import kr.desponline.desp_backend.repository.mongodb.RPGSharpRepository;
import org.springframework.stereotype.Service;

@Service
public class RankingService {

    private final RPGSharpRepository rpgSharpRepository;

    public RankingService(RPGSharpRepository rpgSharpRepository) {
        this.rpgSharpRepository = rpgSharpRepository;
    }

    public List<PlayerEntity> readTopPlayers(long limit) {
        return rpgSharpRepository.readPlayerOrderByLevel().take(limit).collectList().block();
    }
}
