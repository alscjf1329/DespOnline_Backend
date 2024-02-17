package kr.desponline.desp_backend.service;

import java.time.LocalDateTime;
import java.util.List;
import kr.desponline.desp_backend.dto.CashChargeLogDTO;
import kr.desponline.desp_backend.entity.despdb.CashChargeLogEntity;
import kr.desponline.desp_backend.mysql_repository.despdb.CashChargeLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CashChargeLogService {
    private final CashChargeLogRepository cashChargeLogRepository;

    @Autowired
    public CashChargeLogService(CashChargeLogRepository cashChargeLogRepository) {
        this.cashChargeLogRepository = cashChargeLogRepository;
    }

    public List<CashChargeLogEntity> findAll() {
        return cashChargeLogRepository.findAll();
    }

    public void createCashChargeLog(CashChargeLogDTO cashChargeLogDTO, String uuid) {
        CashChargeLogEntity cashChargeLogEntity = new CashChargeLogEntity();
        int cash = Integer.parseInt(cashChargeLogDTO.getAmount());
        cashChargeLogEntity.setToss_order_number(cashChargeLogDTO.getToss_order_number());
        cashChargeLogEntity.setNick_name(cashChargeLogDTO.getNick_name());
        cashChargeLogEntity.setUser_uuid(uuid);
        cashChargeLogEntity.setAmount(cash);
        cashChargeLogEntity.setCharged_cash(cash);
        cashChargeLogEntity.setCharge_at(LocalDateTime.now());
        cashChargeLogRepository.save(cashChargeLogEntity);
    }

}
