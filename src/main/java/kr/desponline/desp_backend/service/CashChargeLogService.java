package kr.desponline.desp_backend.service;

import kr.desponline.desp_backend.dto.CashChargeLogDTO;
import kr.desponline.desp_backend.entity.CashChargeLogEntity;
import kr.desponline.desp_backend.mysql_repository.CashChargeLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CashChargeLogService {
    CashChargeLogRepository cashChargeLogRepository;

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
        cashChargeLogEntity.setCharge_at(cashChargeLogDTO.getCharge_at());
        cashChargeLogRepository.save(cashChargeLogEntity);
    }

}
