package com.nttdata.transaction.application;

import com.nttdata.transaction.domain.AccountAffiliation;
import com.nttdata.transaction.infraestructure.model.dao.AccountAffiliationDao;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
/**
 * ACCOUNTAFFILIATIONREPOSITORY.
 * Define las operaciones en la BD para
 * la afiliacion de una cuenta bancaria y un cliente
 */
public interface AccountAffiliationRepository {
    /**
     * Registro (Afiliación) de un cliente con cuenta bancaria.
     * @param accountAffiliation
     * @return Mono<AccountAffiliation>
     */
    Mono<AccountAffiliation> create(AccountAffiliation accountAffiliation);

    /**
     * Actualización de un cliente con cuenta bancaria.
     * @param id
     * @param accountAffiliation
     * @return Mono<AccountAffiliation>
     */
    Mono<AccountAffiliation> update(String id,
                                    AccountAffiliation accountAffiliation);

    /**
     * Eliminación de un cliente con cuenta bancaria.
     * @param id
     * @return Mono<AccountAffiliationDao>
     */
    Mono<AccountAffiliationDao> delete(String id);

    /**
     * Busqueda de un cliente con cuenta bancaria por Id.
     * @param id
     * @return Mono<AccountAffiliation>
     */
    Mono<AccountAffiliation> findById(String id);

    /**
     * Busqueda de todos los clientes con cuenta bancaria.
     * @return Flux<AccountAffiliation>
     */
    Flux<AccountAffiliation> findAll();
}
