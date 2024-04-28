package kr.desponline.desp_backend.service;

import kr.desponline.desp_backend.entity.mysql.webgamedb.GameUserEntity;
import kr.desponline.desp_backend.mysql_repository.webgamedb.GameUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public void save(GameUserEntity gameUserEntity) {
        this.gameUserRepository.save(gameUserEntity);
    }

    public GameUserEntity findGameUserEntityById(String id) {
        return this.gameUserRepository.findGameUserEntityById(id);
    }

    @Transactional(transactionManager = "webgamedbTransactionManager")
    public void changeId(String uuid, String newId) {
        GameUserEntity gameUser =
            this.gameUserRepository.findGameUserEntityByUuid(uuid);

        if (this.gameUserRepository.existsById(newId)) {
            return;
        }
        gameUser.updateId(newId);
    }

    @Transactional(transactionManager = "webgamedbTransactionManager")
    public void changePassword(String uuid, String newPassword) {
        GameUserEntity gameUser =
            this.gameUserRepository.findGameUserEntityByUuid(uuid);

        gameUser.updatePassword(newPassword);
    }

    public boolean existsId(final String id) {
        return gameUserRepository.existsById(id);
    }
}
