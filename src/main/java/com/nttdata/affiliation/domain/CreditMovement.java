package com.nttdata.affiliation.domain;

import com.nttdata.affiliation.domain.bean.Account;
import com.nttdata.affiliation.domain.bean.Credit;
import com.nttdata.affiliation.domain.bean.CreditAffiliation;
import com.nttdata.affiliation.domain.bean.Customer;
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
     * Codigo de afiliación de credito.
     */
    private String idCreditAffiliation;
    /**
     * Datos del cliente.
     */
    private Customer customer;
    /**
     * Datos del credito.
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
