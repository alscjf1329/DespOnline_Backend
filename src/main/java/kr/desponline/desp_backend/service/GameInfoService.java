package kr.desponline.desp_backend.service;

import kr.desponline.desp_backend.entity.GameInfoEntity;
import kr.desponline.desp_backend.entity.GameInfoEntity.ProcessedGameInfoEntity;
import kr.desponline.desp_backend.mysql_repository.GameInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameInfoService {

    @Value("${webgame.reward.default-count.ss}")
    private int rewardSS;
    @Value("${webgame.reward.default-count.s}")
    private int rewardS;
    @Value("${webgame.reward.default-count.a}")
    private int rewardA;
    @Value("${webgame.reward.default-count.b}")
    private int rewardB;
    @Value("${webgame.reward.default-count.c}")
    private int rewardC;

    @Qualifier(value = "webgamedbDataSource")
    GameInfoRepository gameInfoRepository;

    @Autowired
    public GameInfoService(GameInfoRepository gameInfoRepository) {
        this.gameInfoRepository = gameInfoRepository;
    }

    @Transactional
    public ProcessedGameInfoEntity saveGameInfo(String nickname) {
        GameInfoEntity gameInfoEntity = gameInfoRepository.findGameInfoEntityByNickname(
            nickname);
        if (gameInfoEntity == null) {
            return gameInfoRepository.save(insertNewGameInfoEntity(nickname))
                .toProcessedGameInfoEntity();
        }
        return gameInfoEntity.toProcessedGameInfoEntity();
    }

    public GameInfoEntity insertNewGameInfoEntity(String nickname) {
        GameInfoEntity newGameInfoEntity = new GameInfoEntity();
        newGameInfoEntity.setNickname(nickname);
        newGameInfoEntity.setBoard_status(null);
        newGameInfoEntity.setSs(rewardSS);
        newGameInfoEntity.setSs(rewardS);
        newGameInfoEntity.setSs(rewardA);
        newGameInfoEntity.setSs(rewardB);
        newGameInfoEntity.setSs(rewardC);
        return gameInfoRepository.save(newGameInfoEntity);
    }
}
