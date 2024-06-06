package kr.desponline.desp_backend.service;

import kr.desponline.desp_backend.dto.RewardMailRequestDTO;
import kr.desponline.desp_backend.entity.mongodb.RewardEntity;
import kr.desponline.desp_backend.entity.mongodb.RewardMailBoxEntity;
import kr.desponline.desp_backend.entity.mongodb.RewardMailBoxEntity.Mail;
import kr.desponline.desp_backend.repository.mongodb.RewardMailRepository;
import kr.desponline.desp_backend.repository.mongodb.RewardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RewardMailBoxService {

    private final RewardRepository rewardRepository;

    private final RewardMailRepository rewardMailRepository;

    @Autowired
    public RewardMailBoxService(
        RewardMailRepository rewardMailRepository,
        RewardRepository rewardRepository
    ) {
        this.rewardMailRepository = rewardMailRepository;
        this.rewardRepository = rewardRepository;
    }

    public void sendRewardMail(RewardMailRequestDTO gameMailRequestDTO) {
        RewardEntity reward = rewardRepository.findByName(gameMailRequestDTO.getRewardName())
            .block();

        if (reward == null) {
            return;
        }

        for (String uuid : gameMailRequestDTO.getUuids()) {
            RewardMailBoxEntity rewardMailBox = rewardMailRepository.findByUuid(uuid).block();
            if (rewardMailBox == null) {
                rewardMailBox = RewardMailBoxEntity.createDefault(uuid);
            }

            RewardMailBoxEntity.Mail mail = new Mail(
                gameMailRequestDTO.getSenderName(),
                gameMailRequestDTO.getLetter(),
                reward.getContent().getSerializedInventory(),
                reward.getContent().getMoney()
            );
            rewardMailBox.addMail(mail);
            rewardMailRepository.save(rewardMailBox).subscribe();
        }
    }
}