package com.nttdata.affiliation.infraestructure.model.dao;

import com.nttdata.affiliation.domain.CreditMovementType;
import com.nttdata.affiliation.domain.bean.CreditAffiliation;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * CREDITMOVEMENTDAO.
 * Contiene los atributos del documento Movimiento de credito.
 * Para la persistencia.
 */
@Data
@Document("creditMovement")
public class CreditMovementDao {
    /**
     * Codigo del movimiento - transacción.
     */
    @Id
    private String id;
    /**
     * Codigo de afiliación de credito.
     */
    private String idCreditAffiliation;
    /**
     * Datos de la afiliacion de credito.
     */
    @Transient
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
