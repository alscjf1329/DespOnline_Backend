package kr.desponline.desp_backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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
    private Date charge_at;

}
