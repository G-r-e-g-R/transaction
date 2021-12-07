package com.nttdata.transaction.domain;

import com.nttdata.transaction.domain.bean.Account;
import com.nttdata.transaction.domain.bean.Customer;
import lombok.Data;

import java.util.Date;

/**
 * ACCOUNTMOVEMENT.
 * La clase Movimiento de Cuenta Bancarias contendrá
 * información de las operaciones de deposito y retiro
 */
@Data
public class AccountMovement {
    /**
     * Codigo del movimiento - transacción.
     */
    private String id;
    /**
     * Datos del cliente.
     */
    private Customer customer;
    /**
     * Datos de la cuenta bancaria.
     */
    private Account account;
    /**
     * Monto de la transacción.
     */
    private Double amount;
    /**
     * Tipo de movimiento - transacción.
     */
    private AccountMovementType movementType;
    /**
     * Fecha del movimiento - transacción.
     */
    private Date movementDate;
}
