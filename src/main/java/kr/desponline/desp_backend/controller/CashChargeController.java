package kr.desponline.desp_backend.controller;

import kr.desponline.desp_backend.dto.CashChargeDTO;
import kr.desponline.desp_backend.dto.CashChargeLogDTO;
import kr.desponline.desp_backend.entity.UserEntity;
import kr.desponline.desp_backend.service.CashChargeLogService;
import kr.desponline.desp_backend.service.SearchService;
import kr.desponline.desp_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
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

    @GetMapping("/existuser/{nickname}")
    public ResponseEntity isExistUser(@PathVariable String nickname) {
        String uuid = searchService.findUuidByNickname(nickname);
        if (uuid == null) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
        UserEntity user = userService.findUserEntityByUuid(uuid);
        if (user == NOT_EXIST_USER) {
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @GetMapping("/existcash/{money}")
    public ResponseEntity isExistCash(@PathVariable String money) {
        if (money.matches("\\d+")) {
            return new ResponseEntity<>(Integer.parseInt(money) < 100000, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    @PutMapping("/charge")
    public RedirectView chargeCash(@RequestBody CashChargeDTO cashChargeDTO) {
        String uuid = searchService.findUuidByNickname(cashChargeDTO.getNick_name());
        UserEntity user = userService.findUserEntityByUuid(uuid);
        int cash = Integer.parseInt(cashChargeDTO.getAmount());
        int legacyCash = user.getCash();
        System.out.println("legacyCash: " + legacyCash);
        System.out.println("cash: " + cash);
        System.out.println("legacyCash + cash: " + (legacyCash + cash));
        user.setCash(legacyCash + cash);
        userService.updateUser(user);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("redirect:/");
        return redirectView;
    }

    @PostMapping("/chargelog")
    public boolean createChargeLog(@RequestBody CashChargeLogDTO cashChargeLogDTO) {
        String nickname = cashChargeLogDTO.getNick_name();
        String uuid = searchService.findUuidByNickname(nickname);
        cashChargeLogService.createCashChargeLog(cashChargeLogDTO, uuid);
        return true;
    }
}
