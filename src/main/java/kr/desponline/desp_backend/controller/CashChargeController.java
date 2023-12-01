package kr.desponline.desp_backend.controller;

import kr.desponline.desp_backend.dto.CashChargeDTO;
import kr.desponline.desp_backend.dto.CashChargeLogDTO;
import kr.desponline.desp_backend.entity.CashChargeLogEntity;
import kr.desponline.desp_backend.entity.UserEntity;
import kr.desponline.desp_backend.service.CashChargeLogService;
import kr.desponline.desp_backend.service.SearchService;
import kr.desponline.desp_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cash")
public class CashChargeController {
    private static final UserEntity NOT_EXIST_USER = null;
    private final CashChargeLogService cashChargeLogService;
    private final UserService userService;
    private final SearchService searchService;


    @Autowired
    public CashChargeController(CashChargeLogService cashChargeLogService, UserService userService, SearchService searchService) {
        this.cashChargeLogService = cashChargeLogService;
        this.userService = userService;
        this.searchService = searchService;
    }

    @GetMapping("/sightchargelog")
    public List<CashChargeLogEntity> getAllCashChargeLogs() {
        return cashChargeLogService.findAll();
    }

    @GetMapping("/existuser/{nickname}")
    public boolean isExistUser(@PathVariable String nickname) {
        String uuid = searchService.findUuidByNickname(nickname);
        if (uuid == null) {
            return false;
        }
        UserEntity user = userService.findUserEntityByUuid(uuid);
        if (user == NOT_EXIST_USER) {
            return false;
        }
        return true;
    }

    @GetMapping("/existcash/{money}")
    public boolean isExistCash(@PathVariable String money) {
        if (money.matches("\\d+")) {
            return Integer.parseInt(money) < 100000;
        }
        return false;
    }

    @PostMapping("/charge")
    public ResponseEntity<String> chargeCash(@RequestBody CashChargeDTO cashChargeDTO) {
        String uuid = searchService.findUuidByNickname(cashChargeDTO.getNick_name());
        UserEntity user = userService.findUserEntityByUuid(uuid);
        int cash = Integer.parseInt(cashChargeDTO.getAmount());
        user.setCash(user.getCash() + cash);
        userService.updateUser(user);
        return ResponseEntity.ok().body("충전이 완료되었습니다.");
    }

    @PostMapping("/chargelog")
    public ResponseEntity<String> createChargeLog(@RequestBody CashChargeLogDTO cashChargeLogDTO) {
        String nickname = cashChargeLogDTO.getNick_name();
        String uuid = searchService.findUuidByNickname(nickname);
        cashChargeLogService.createCashChargeLog(cashChargeLogDTO, uuid);
        return ResponseEntity.ok().body("충전 내역이 저장되었습니다.");
    }
}
