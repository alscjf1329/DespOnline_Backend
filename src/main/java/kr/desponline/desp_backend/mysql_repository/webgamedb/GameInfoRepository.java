package kr.desponline.desp_backend.mysql_repository.webgamedb;

import kr.desponline.desp_backend.entity.webgamedb.GameInfoEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameInfoRepository extends JpaRepository<GameInfoEntity, Long> {

    GameInfoEntity findGameInfoEntityByNickname(String nickname);
}
