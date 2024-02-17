package kr.desponline.desp_backend.entity.despdb;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Qualifier;

@Entity
@Getter
@Setter
@Qualifier(value = "entityManagerFactory")
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
