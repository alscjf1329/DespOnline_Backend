package kr.desponline.desp_backend.repository.mysql.despdb;

import kr.desponline.desp_backend.entity.mysql.despdb.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findUserEntityByUuid(String uuid);
}