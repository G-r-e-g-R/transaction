package com.nttdata.transaction.domain;

import com.nttdata.transaction.domain.bean.Account;
import com.nttdata.transaction.domain.bean.Customer;
import lombok.Data;

import java.util.Date;

/**
 * ACCOUNTMOVEMENT: La clase Movimiento de Cuenta Bancarias contendrá  información de las operaciones de deposito y retiro
 */
@Data
public class AccountMovement {
    private String id;
    private Customer customer;
    private Account account;
    private Double amount;
    private AccountMovementType movementType;
    private Date movementDate;
}
