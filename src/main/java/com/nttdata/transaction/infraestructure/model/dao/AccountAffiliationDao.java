package com.nttdata.transaction.infraestructure.model.dao;

import com.nttdata.transaction.domain.bean.Account;
import com.nttdata.transaction.domain.bean.Customer;
import com.nttdata.transaction.domain.bean.Status;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * ACCOUNTAFFILIATIONDAO: Contiene los atributos del documento Afiliación de cuentas bancarias
 *                        (accountAffiliation) para la persistencia
 */
@Data
@Document("accountAffiliation")
public class AccountAffiliationDao {
    @Id
    private String id;
    private String idCustomer;          // Identificador del cliente
    private String idAccount;           // Identificador de la cuenta bancaria
    @Transient
    private Customer customer;          // Datos del cliente
    @Transient
    private Account account;            // Datos de la cuenta bancaria
    private String number;              // Numero de cuenta bancaria
    private String movementDay;         // Movimiento (retiro o deposito) en un dia especifico
    private int numberOfHolder;         // Numero de titulares
    private int numberOfSigner;         // Numero de firmantes
    private Double baseAmount;          // Monto en el momento de la apertura de la cuenta
    private Double balance;             // Saldo disponible
    private Status status;              // Estado Activo o Inactivo
}
