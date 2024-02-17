package kr.desponline.desp_backend.mysql_repository.despdb;

import kr.desponline.desp_backend.entity.despdb.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findUserEntityByUuid(String uuid);
}
