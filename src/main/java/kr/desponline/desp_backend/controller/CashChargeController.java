package kr.desponline.desp_backend.controller;

import kr.desponline.desp_backend.entity.CashChargeLogEntity;
import kr.desponline.desp_backend.mysql_repository.CashChargeLogRepository;
import kr.desponline.desp_backend.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cash")
public class CashChargeController {
    private final CashChargeLogRepository cashChargeLogRepository;
    private final SearchService searchService;

    @Autowired
    public CashChargeController(CashChargeLogRepository cashChargeLogRepository, SearchService searchService) {
        this.cashChargeLogRepository = cashChargeLogRepository;
        this.searchService = searchService;
    }

    @GetMapping("/chargelog")
    public List<CashChargeLogEntity> getAllCashChargeLogs() {
        return cashChargeLogRepository.findAll();
    }

    @GetMapping("/existuser/{nickname}")
    public boolean isExistUser(@PathVariable String nickname) {
        return searchService.findRecordByNickname(nickname) != null;
    }


}
