package kr.desponline.desp_backend.mysql_repository;

import kr.desponline.desp_backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findUserEntityByUuid(String uuid);
}
