package kr.desponline.desp_backend.mysql_repository.webgamedb;

import kr.desponline.desp_backend.entity.mysql.webgamedb.GameUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameUserRepository extends JpaRepository<GameUserEntity, Long> {

    boolean existsByUuid(String uuid);

    boolean existsById(String id);

    GameUserEntity findGameUserEntityById(String id);

    GameUserEntity findGameUserEntityByUuid(String uuid);
}
