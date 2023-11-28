package kr.desponline.desp_backend.mysql_repository;

import kr.desponline.desp_backend.entity.CashChargeLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashChargeLogRepository extends JpaRepository<CashChargeLogEntity, Long> {
}
