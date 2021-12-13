package com.nttdata.affiliation.application;

import com.nttdata.affiliation.domain.AccountMovement;
import com.nttdata.affiliation.domain.bean.Account;
import com.nttdata.affiliation.domain.bean.AccountAffiliation;
import com.nttdata.affiliation.domain.bean.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ACCOUNTMOVEMENTOPERATIONS.
 * Define las operaciones (CRUD) de los movimientos
 *                               de Cuentas Bancarias de un cliente.
 */
public interface AccountMovementOperations {
    /**
     * Registra un movimiento de cuenta bancaria.
     * @param accountMovement movimiento.
     * @return Mono<AccountMovement>
     */
    Mono<AccountMovement>
    create(AccountMovement accountMovement);

    /**
     * Actualización un movimiento de cuenta bancaria.
     * @param id codigo.
     * @param accountMovement movimiento de cuenta.
     * @return Mono<AccountAffiliation>
     */
    Mono<AccountMovement>
    update(String id, AccountMovement accountMovement);

    /**
     * Eliminación de un movimiento de cuenta bancaria.
     * @param id codigo.
     * @return Mono<AccountMovement>
     */
    Mono<Void>
    delete(String id);

    /**
     * Busqueda de un movimiento de cuenta bancaria.
     * @param id codigo.
     * @return Mono<AccountMovement>
     */
    Mono<AccountMovement>
    findById(String id);

    /**
     * Busqueda de todas los movimiento de cuenta bancaria.
     * @return Flux<AccountMovement>
     */
    Flux<AccountMovement>
    findAll();


    /**
     * Afiliacion de una cuenta del Cliente.
     * @param idAffiliation Codigo de la afilicacion.
     * @return Mono<AccountAffiliation>
     */
    Mono<AccountAffiliation>
    getAccountAffiliationById(String idAffiliation);

    /**
     * Actualiza la afiliacion de cuentas.
     * @param idAffiliation codigo de afiliacion.
     * @param accountAffiliation datos de la afiliacion.
     * @return Mono<AccountAffiliation>
     */
    Mono<AccountAffiliation>
    putAccountAffiliation(String idAffiliation, AccountAffiliation accountAffiliation);

    /**
     * Obtenemos los datos del cliente.
     * @param idCustomer Codigo del cliente.
     * @return Flux<Customer>
     */
    Mono<Customer>
    getCustomerById(final String idCustomer);

    /**
     * Obtenemos los datos del producto: Cuenta Bancaria.
     * @param idAccount codigo de la cuenta bancaria
     * @return Flux<Account>
     */
    Mono<Account>
    getProductAccountById(final String idAccount);
    /**
     * Listado de movmientos de cuentas por Afiliacion.
     * @param idAffiliation Codigo de afiliacion.
     * @return Flux<AccountMovement>
     */
    Flux<AccountMovement>
    findByIdAffiliation(String idAffiliation);


}
