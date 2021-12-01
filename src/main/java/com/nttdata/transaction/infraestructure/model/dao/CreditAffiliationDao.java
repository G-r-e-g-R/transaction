package com.nttdata.transaction.infraestructure.model.dao;

import com.nttdata.transaction.domain.bean.Credit;
import com.nttdata.transaction.domain.bean.Customer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * CREDITAFFILIATIONDAO: Contiene los atributos del documento Afiliación de Creditos
 *                        (creditAffiliation) para la persistencia
 */
@Data
@Document("creditAffiliation")
public class CreditAffiliationDao {
    @Id
    private String id;
    private String idCustomer;              // Identificador del cliente
    private String idCredit;                // Identificador de la cuenta bancaria
    @Transient
    private Customer customer;              // Datos del cliente
    @Transient
    private Credit credit;                  // Datos del credito
    private Double baseAmount;              // Monto Base al momento del registro del Credito
    private Double balance;                 // Saldo disponible
    private Double creditLimit;             // Limite de Credito
}
