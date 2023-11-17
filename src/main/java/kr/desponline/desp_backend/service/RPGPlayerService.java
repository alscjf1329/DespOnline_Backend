package kr.desponline.desp_backend.service;

import java.util.List;
import kr.desponline.desp_backend.dto.RPGPlayerDTO;
import kr.desponline.desp_backend.mongodb_repository.RPGSharpRepository;
import org.springframework.stereotype.Service;

@Service
public class RPGPlayerService {
    private final RPGSharpRepository rpgSharpRepository;

    public RPGPlayerService(RPGSharpRepository rpgSharpRepository) {
        this.rpgSharpRepository = rpgSharpRepository;
    }

    public List<RPGPlayerDTO> getTopPlayers(long limit) {
        return rpgSharpRepository.findPlayerOrderByLevel().take(limit).collectList().block();
    }
}
