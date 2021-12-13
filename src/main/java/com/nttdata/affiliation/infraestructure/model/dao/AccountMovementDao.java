package com.nttdata.affiliation.infraestructure.model.dao;

import com.nttdata.affiliation.domain.AccountMovementType;
import com.nttdata.affiliation.domain.bean.AccountAffiliation;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

/**
 * ACCOUNTMOVEMENTDAO.
 * Contiene los atributos del documento Movimiento de cuentas bancarias.
 * Para la persistencia.
 */
@Data
@Document("accountMovement")
public class AccountMovementDao {
    /**
     * Codigo del movimiento - transacción.
     */
    @Id
    private String id;
    /**
     * Código de la afiliación.
     */
    private String idAccountAffiliation;
    /**
     * Datos de la afiliación.
     */
    @Transient
    private AccountAffiliation accountAffiliation;
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
