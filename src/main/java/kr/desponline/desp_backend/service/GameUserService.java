package kr.desponline.desp_backend.service;

import kr.desponline.desp_backend.mysql_repository.webgamedb.GameUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameUserService {

    private final GameUserRepository gameUserRepository;

    @Autowired
    public GameUserService(GameUserRepository gameUserRepository) {
        this.gameUserRepository = gameUserRepository;
    }

    public boolean existsByUuid(String uuid) {
        return this.gameUserRepository.existsByUuid(uuid);
    }
}
