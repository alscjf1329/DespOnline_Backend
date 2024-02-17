package kr.desponline.desp_backend.mysql_repository;

import kr.desponline.desp_backend.entity.GameInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameInfoRepository extends JpaRepository<GameInfoEntity, Long> {

    GameInfoEntity findGameInfoEntityByNickname(String nickname);
}
