package com.nttdata.transaction.domain;

import com.nttdata.transaction.domain.bean.Credit;
import com.nttdata.transaction.domain.bean.Customer;
import lombok.Data;

/**
 * CREDITMOVEMENT: La clase Movimiento de Creditos contendrá  información de
 *                 las operaciones de pagos y consumos
 */
@Data
public class CreditMovement {
    private String id;
    private Customer customer;
    private Credit credit;
    private CreditMovementType movementType;
    private Double amount;
}
