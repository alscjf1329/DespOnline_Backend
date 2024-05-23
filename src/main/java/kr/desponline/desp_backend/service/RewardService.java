package kr.desponline.desp_backend.service;

import java.util.Objects;
import kr.desponline.desp_backend.dto.GameRewardMailRequestDTO;
import kr.desponline.desp_backend.entity.mongodb.RPGPlayerEntity;
import kr.desponline.desp_backend.entity.mongodb.RewardEntity;
import kr.desponline.desp_backend.mongodb_repository.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RewardService {

    @Value("${minecraft.server.address.global}")
    private String pingAddress;

    private final MinecraftPublicAPIService minecraftPublicAPIService;
    private final RPGPlayerService rpgPlayerService;
    private final RewardRepository rewardRepository;
    private final GameMailService gameMailService;

    @Autowired
    public RewardService(
        MinecraftPublicAPIService minecraftPublicAPIService,
        RPGPlayerService rpgPlayerService,
        RewardRepository rewardRepository,
        GameMailService gameMailService) {
        this.minecraftPublicAPIService = minecraftPublicAPIService;
        this.rpgPlayerService = rpgPlayerService;
        this.rewardRepository = rewardRepository;
        this.gameMailService = gameMailService;
    }

    public void sendReward(GameRewardMailRequestDTO gameMailRequestDTO) {
        boolean minecraftServerOnlineStatus = minecraftPublicAPIService.pingToServer(pingAddress);
        RewardEntity rewardEntity = rewardRepository
            .findByName(gameMailRequestDTO.getRewardName()).block();
        if (!minecraftServerOnlineStatus) {
            for (String uuid : gameMailRequestDTO.getUuids()) {
                RPGPlayerEntity rpgPlayerEntity = rpgPlayerService.findByUuid(uuid);
                rpgPlayerEntity.addMail(Objects.requireNonNull(rewardEntity).getMail());
                rpgPlayerService.save(rpgPlayerEntity);
            }
            return;
        }
        gameMailService.sendMail(gameMailRequestDTO);
    }
}