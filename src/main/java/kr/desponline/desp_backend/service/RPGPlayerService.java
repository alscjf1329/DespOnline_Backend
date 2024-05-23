package kr.desponline.desp_backend.service;

import kr.desponline.desp_backend.entity.mongodb.RPGPlayerEntity;
import kr.desponline.desp_backend.mongodb_repository.RPGPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RPGPlayerService {

    private final RPGPlayerRepository rpgPlayerRepository;

    @Autowired
    public RPGPlayerService(RPGPlayerRepository playerMailRepository) {
        this.rpgPlayerRepository = playerMailRepository;
    }

    public RPGPlayerEntity findByUuid(String uuid) {
        return rpgPlayerRepository.findByUuid(uuid).block();
    }

    public RPGPlayerEntity save(RPGPlayerEntity rpgPlayerEntity) {
        return rpgPlayerRepository.save(rpgPlayerEntity).block();
    }
}
