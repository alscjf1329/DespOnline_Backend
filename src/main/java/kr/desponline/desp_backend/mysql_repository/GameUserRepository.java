package kr.desponline.desp_backend.mysql_repository;

import kr.desponline.desp_backend.entity.GameUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameUserRepository extends JpaRepository<GameUserEntity, Long> {

    GameUserEntity findGameUserEntityByNickname(String nickname);

}
