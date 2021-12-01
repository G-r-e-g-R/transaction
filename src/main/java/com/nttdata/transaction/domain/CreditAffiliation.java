package com.nttdata.transaction.domain;


import com.nttdata.transaction.domain.bean.Credit;
import com.nttdata.transaction.domain.bean.Customer;
import lombok.Data;

/**
 * CREDITAFFILIATION: La clase Afiliación de Creditos contendrá  información del
 *                    registro de un credito de un cliente
 */
@Data
public class CreditAffiliation {
    private String id;
    private String idCustomer;              // Identificador del cliente
    private String idCredit;                // Identificador del credito
    private Customer customer;              // Datos del cliente
    private Credit credit;                  // Datos del credito
    private Double baseAmount;              // Monto Base al momento del registro del Credito
    private Double balance;                 // Saldo disponible
    private Double creditLimit;             // Limite de Credito
}
