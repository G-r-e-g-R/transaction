package com.nttdata.affiliation.domain;

import com.nttdata.affiliation.domain.bean.CreditAffiliation;
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
     * Datos de la afiliacion de credito.
     */
    private CreditAffiliation creditAffiliation;
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
