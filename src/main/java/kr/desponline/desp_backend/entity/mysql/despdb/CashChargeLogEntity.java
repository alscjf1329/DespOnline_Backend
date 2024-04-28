package kr.desponline.desp_backend.entity.mysql.despdb;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "cash_charge_log")
public class CashChargeLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String toss_order_number;
    private String nick_name;
    private String user_uuid;
    private int amount;
    private int charged_cash;
    private LocalDateTime charge_at;
}
