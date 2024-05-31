package kr.desponline.desp_backend.entity.mongodb.web_event.details;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import lombok.Getter;

@Getter
public class CardFlippingDetail extends WebEventDetail {

    public CardFlippingDetail(HashMap<String, Object> details) {
        super(details);
    }

    public int getSize() {
        return (int) details.get("size");
    }

    public int getMaxOpportunity() {
        return (int) details.get("maxOpportunity");
    }

    public int getFlipCount() {
        return (int) details.get("flipCount");
    }

    public List<List<String>> getRewards() {
        return (List<List<String>>) details.get("rewards");
    }

    public String getRandomReward(int rewardLevel) {
        List<String> rewardsInRewardLevel = getRewards().get(rewardLevel);

        // rewardsInRewardLevel 리스트가 비어있는지 확인
        if (rewardsInRewardLevel == null || rewardsInRewardLevel.isEmpty()) {
            return null; // 보상이 없는 경우 null 반환 또는 다른 처리 방법 선택
        }

        // Random 클래스를 사용하여 리스트에서 랜덤한 요소 선택
        Random random = new Random();
        int randomIndex = random.nextInt(rewardsInRewardLevel.size());
        return rewardsInRewardLevel.get(randomIndex);
    }
}
