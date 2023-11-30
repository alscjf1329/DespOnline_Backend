package kr.desponline.desp_backend.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.Date;

@Getter
public class CashChargeLogDTO {
    private String toss_order_number;
    private String nick_name;
    private String amount;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date charge_at;
}
