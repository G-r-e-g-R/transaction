package com.nttdata.transaction.domain;

import com.nttdata.transaction.domain.bean.Credit;
import com.nttdata.transaction.domain.bean.Customer;
import lombok.Data;

import java.util.Date;

/**
 * CREDITMOVEMENT.
 * La clase Movimiento de Creditos contendrá  información de
 *                 las operaciones de pagos y consumos
 */
@Data
public class CreditMovement {
    /**
     * Codigo del movimiento - transacción.
     */
    private String id;
    /**
     * Datos del cliente.
     */
    private Customer customer;
    /**
     * Datos del crédito.
     */
    private Credit credit;
    /**
     * Tipo de movimiento - transacción.
     */
    private CreditMovementType movementType;
    /**
     * Monto de la transacción.
     */
    private Double amount;
    /**
     * Fecha del movimiento - transacción.
     */
    private Date movementDate;
}
