package com.nttdata.transaction.domain;

import com.nttdata.transaction.domain.bean.Account;
import com.nttdata.transaction.domain.bean.Customer;
import com.nttdata.transaction.domain.bean.Status;
import lombok.Data;


/**
 * ACCOUNTAFFILIATION: La clase Afiliación de Cuenta Bancarias contendrá  información del
 *                     registro de una cuenta bancaria de un cliente
 */
@Data
public class AccountAffiliation {
    private String id;
    private String idCustomer;          // Identificador del cliente
    private String idAccount;           // Identificador de la cuenta bancaria
    private Customer customer;          // Datos del cliente
    private Account account;            // Datos de la cuenta bancaria
    private String number;              // Numero de cuenta bancaria
    private String movementDay;         // Movimiento (retiro o deposito) en un dia especifico
    private int numberOfHolder;         // Numero de titulares
    private int numberOfSigner;         // Numero de firmantes
    private Double baseAmount;          // Monto en el momento de la apertura de la cuenta
    private Double balance;             // Saldo disponible
    private Status status;              // Estado Activo o Inactivo

}
