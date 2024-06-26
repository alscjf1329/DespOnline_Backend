package kr.desponline.desp_backend.repository.mysql.despdb;

import kr.desponline.desp_backend.entity.mysql.despdb.CashChargeLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CashChargeLogRepository extends JpaRepository<CashChargeLogEntity, Long> {

}
