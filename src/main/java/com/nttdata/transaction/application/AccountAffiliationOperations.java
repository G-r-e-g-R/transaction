package com.nttdata.transaction.application;

import com.nttdata.transaction.domain.AccountAffiliation;
import com.nttdata.transaction.infraestructure.model.dao.AccountAffiliationDao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * ACCOUNTAFFILIATIONOPERATIONS.
 * Define las operaciones (CRUD) de la afiliación
 *                               de Cuentas Bancarias de un cliente.
 */
public interface AccountAffiliationOperations {
    /**
     * Creación de una cuenta bancaria para un cliente.
     * @param accountAffiliation
     * @return Mono<AccountAffiliation>
     */
    Mono<AccountAffiliation> create(AccountAffiliation accountAffiliation);

    /**
     * Actualización de una cuenta bancaria para un cliente.
     * @param id
     * @param accountAffiliation
     * @return Mono<AccountAffiliation>
     */
    Mono<AccountAffiliation> update(
            String id,
            AccountAffiliation accountAffiliation);

    /**
     * Eliminación de una cuenta bancaria para un cliente.
     * @param id
     * @return Mono<AccountAffiliationDao>
     */
    Mono<AccountAffiliationDao> delete(String id);

    /**
     * Busqueda de una cuenta bancaria de un cliente por Id.
     * @param id
     * @return Mono<AccountAffiliation>
     */
    Mono<AccountAffiliation> findById(String id);

    /**
     * Busqueda de todas las cuentas bancarias de los clientes.
     * @return Flux<AccountAffiliation>
     */
    Flux<AccountAffiliation> findAll();
}
