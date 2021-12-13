package com.nttdata.affiliation.application;

import com.nttdata.affiliation.domain.AccountMovement;
import com.nttdata.affiliation.domain.CreditMovement;
import com.nttdata.affiliation.domain.bean.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * CREDITMOVEMENTREPOSITORY.
 * Define las operaciones en la BD para
 * los movimientos de un credito y un cliente
 */
public interface CreditMovementRepository {
    /**
     * Registra un movimiento de un credito.
     * @param creditMovement movimiento.
     * @return Mono<CreditMovement>
     */
    Mono<CreditMovement>
    create(CreditMovement creditMovement);

    /**
     * Actualización un movimiento de credito.
     * @param id codigo.
     * @param creditMovement movimiento de credito.
     * @return Mono<CreditMovement>
     */
    Mono<CreditMovement>
    update(String id, CreditMovement creditMovement);

    /**
     * Eliminación de un movimiento de credito.
     * @param id codigo.
     * @return Mono<CreditMovement>
     */
    Mono<Void>
    delete(String id);

    /**
     * Busqueda de un movimiento de credito.
     * @param id codigo.
     * @return Mono<CreditMovement>
     */
    Mono<CreditMovement>
    findById(String id);

    /**
     * Busqueda de todas los movimiento de credito.
     * @return Flux<CreditMovement>
     */
    Flux<CreditMovement>
    findAll();

    /**
     * Actualiza la afiliacion de cuentas.
     * @param idAffiliation codigo de afiliacion.
     * @param creditAffiliation datos de la afiliacion.
     * @return Mono<CreditAffiliation>
     */
    Mono<CreditAffiliation>
    putCreditAffiliation(String idAffiliation, CreditAffiliation creditAffiliation);

    /**
     * Listado de Afiliaciones de cuentas por Cliente.
     * @param idCustomer Codigo de la afilicacion.
     * @return Mono<CreditAffiliation>
     */
    Mono<CreditAffiliation>
    getCreditAffiliationById(String idCustomer);

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
    Mono<Credit>
    getProductCreditById(final String idAccount);

    /**
     * Listado de movimientos de credito por Afiliacion.
     * @param idAffiliation Codigo de afiliacion.
     * @return Flux<CreditMovement>
     */
    Flux<CreditMovement>
    findByIdAffiliation(String idAffiliation);
}
