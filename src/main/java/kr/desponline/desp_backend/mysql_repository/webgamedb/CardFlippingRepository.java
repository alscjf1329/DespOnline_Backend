package kr.desponline.desp_backend.mysql_repository.webgamedb;

import kr.desponline.desp_backend.entity.mysql.webgamedb.CardFlippingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardFlippingRepository extends JpaRepository<CardFlippingEntity, Long> {

    CardFlippingEntity findByUserUuidAndEventId(String uuid, String eventId);
}